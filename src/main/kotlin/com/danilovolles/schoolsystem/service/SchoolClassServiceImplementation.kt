package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.*
import com.danilovolles.schoolsystem.entity.SchoolClass
import com.danilovolles.schoolsystem.entity.Student
import com.danilovolles.schoolsystem.entity.Teacher
import com.danilovolles.schoolsystem.exception.*
import com.danilovolles.schoolsystem.repository.SchoolClassRepository
import com.danilovolles.schoolsystem.repository.StudentRepository
import com.danilovolles.schoolsystem.repository.TeacherRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class SchoolClassServiceImplementation : SchoolClassService {

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var schoolClassRepository: SchoolClassRepository

    override fun createClass(newClass: SchoolClassInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            this.verifyIfSchoolClassExists(newClass)
            val teacher = findTeacherById(newClass.teacherId)
            val students = findStudentsByIdSet(newClass.students)

            this.checkNumberOfStudents(students)
            this.checkTeacherSubjectFitsClassSubject(newClass)

            val savingClass = SchoolClass(
                id = null,
                name = newClass.name,
                subject = newClass.subject,
                description = newClass.description,
                teacher = teacher,
                students = students,
                active = true
            )

            schoolClassRepository.save(savingClass)

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Class created successfully"))

        } catch (e: SchoolClassAlreadyExistsException){
            throw SchoolClassAlreadyExistsException(e.localizedMessage)

        } catch (e: Exception){
            e.stackTrace
            throw SchoolClassServiceLogicException(e.localizedMessage)
        }
    }

    override fun getAllClasses(): ResponseEntity<ApiResponseDTO<Any?>> {
        try {

            val classes: List<SchoolClass> = schoolClassRepository.findAll()
            val classesList: MutableList<SchoolClassOutputDTO> = mutableListOf()

            for (schoolClass in classes) {
                val teacher = schoolClass.teacher
                val teacherOutput = TeacherOutputDTO(
                    name = teacher?.name ?: "null",
                    subject = teacher?.subject ?: "null",
                    email = teacher?.email ?: "null",
                    active = teacher?.active ?: false
                )

                val classOutput = SchoolClassOutputDTO(
                    subject = schoolClass.subject,
                    name = schoolClass.name,
                    teacher = teacherOutput,
                    description = schoolClass.description
                )
                classesList.add(classOutput)
            }

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, classesList))

        } catch (e: Exception) {
            e.stackTrace
            throw SchoolClassServiceLogicException(e.localizedMessage)
        }
    }

    @Transactional
    override fun insertStudent(studentsIds: InsertStudentSetInClassDTO, classId: Long): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            val schoolClass = findClass(classId)
            val students = mergeStudentsFromClassAndDTO(schoolClass, studentsIds.students)

            checkNumberOfStudents(students)

            schoolClass.students = students

            schoolClassRepository.save(schoolClass)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Students added successfully"))

        } catch (e: SchoolClassNotFoundException){
            throw SchoolClassNotFoundException(e.localizedMessage)

        } catch (e: StudentNotFoundException){
            throw StudentNotFoundException(e.localizedMessage)

        } catch (e: Exception){
            e.stackTrace
            throw SchoolClassServiceLogicException(e.localizedMessage)
        }
    }

    override fun deactivateClass(classId: Long): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val schoolClass = findClass(classId)

            schoolClass.active = false

            schoolClassRepository.save(schoolClass)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Class inactivated successfully"))

        } catch (e: SchoolClassNotFoundException){
            throw SchoolClassNotFoundException(e.localizedMessage)

        } catch (e: Exception){
            e.stackTrace
            throw SchoolClassServiceLogicException(e.localizedMessage)
        }
    }

    private fun findClass(classId: Long): SchoolClass {
        return schoolClassRepository.findSchoolClassById(classId)
            ?: throw SchoolClassNotFoundException("Class not found in Database")
    }

    private fun verifyIfSchoolClassExists(schoolClass: SchoolClassInputDTO): Unit? {
        val bySubject = schoolClassRepository.findSchoolClassBySubject(schoolClass.subject)
        val byName = schoolClassRepository.findSchoolClassByName(schoolClass.name)

        if (bySubject != null && byName != null) {
            throw SchoolClassAlreadyExistsException("SchoolClass already in our database")
        }

        return null
    }

    private fun findTeacherById(teacherId: UUID): Teacher? {
        return teacherRepository
            .findById(teacherId)
            .orElseThrow { TeacherNotFoundException("Teacher not found") }
    }

    private fun findStudentsByIdSet(studentsIds: List<UUID>?): MutableList<Student> {
        if (studentsIds == null) {
            throw StudentNotFoundException("student set is null")
        }
        return studentRepository.findAllById(studentsIds)
    }

    private fun checkNumberOfStudents(students: List<Student>?) {
        if (students != null && students.count() > 10) {
            throw SchoolClassServiceLogicException("Classes must not have more than 10 students")
        }
    }

    private fun mergeStudentsFromClassAndDTO(schoolClass: SchoolClass, newStudentsIds: Set<UUID>): MutableList<Student> {
        val currentStudents = schoolClass.students ?: mutableListOf()
        val newStudents = studentRepository.findAllById(newStudentsIds)
        return (currentStudents + newStudents).toMutableList()
    }

    private fun checkTeacherSubjectFitsClassSubject(newClass: SchoolClassInputDTO) {
        val teacher = teacherRepository.findByIdOrNull(newClass.teacherId)
        if (teacher?.subject != newClass.subject) {
            throw SchoolClassServiceLogicException("Teacher and Class subject do not match")
        }
    }

}
