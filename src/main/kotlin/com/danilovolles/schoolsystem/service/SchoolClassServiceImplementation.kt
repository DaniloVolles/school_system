package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.*
import com.danilovolles.schoolsystem.entity.SchoolClass
import com.danilovolles.schoolsystem.entity.Student
import com.danilovolles.schoolsystem.entity.Teacher
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
                .header("Header Name", "header value 1", "header value 2")
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Class created successfully"))

        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }

    override fun getAllClasses(): ResponseEntity<ApiResponseDTO<Any?>> {
        try {

            val classes: List<SchoolClass> = schoolClassRepository.findAll()
            val classesList: MutableList<SchoolClassOutputDTO> = mutableListOf()

            for (schoolClass in classes) {
                val classOutput = SchoolClassOutputDTO(
                    subject = schoolClass.subject,
                    name = schoolClass.name,
                    teacher = schoolClass.teacher,
                    description = schoolClass.description
                )
                classesList.add(classOutput)
            }

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, classesList))

        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }

    @Transactional
    override fun insertStudent(studentsIds: InsertStudentSetInClassDTO, classId: Long): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            val schoolClass = findClass(classId)
            val students = schoolClass.students
            val allStudents = students?.plus(studentRepository.findAllById(studentsIds.students))
            this.checkNumberOfStudents(allStudents)

            schoolClass.students = allStudents as MutableList<Student>?

            schoolClassRepository.save(schoolClass)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Students added successfully"))

        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
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
        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }

    private fun findClass(classId: Long): SchoolClass {
        return schoolClassRepository.findSchoolClassById(classId) ?: throw Exception("Class not found in Database")
    }

    private fun verifyIfSchoolClassExists(schoolClass: SchoolClassInputDTO): Unit? {
        val bySubject = schoolClassRepository.findSchoolClassBySubject(schoolClass.subject)
        val byName = schoolClassRepository.findSchoolClassByName(schoolClass.name)

        if (bySubject != null && byName != null) {
            throw Exception("SchoolClass already in our database")
        }

        return null
    }

    private fun findTeacherById(teacherId: UUID): Teacher? {
        return teacherRepository
            .findById(teacherId)
            .orElseThrow { Exception("Teacher not found") }
    }

    private fun findStudentsByIdSet(studentsIds: List<UUID>?): MutableList<Student> {
        if (studentsIds == null) {
            throw Exception("student set is null")
        }
        return studentRepository.findAllById(studentsIds)
    }

    private fun checkNumberOfStudents(students: List<Student>?) {
        if (students != null && students.count() > 10) {
            throw Exception("Classes must not have more than 10 students")
        }
    }

    private fun findStudentsBySchoolClass(schoolClassId: Long): List<Student>? {
        val schoolClass = schoolClassRepository.findSchoolClassById(schoolClassId) ?: throw Exception("Class not found")
        return schoolClass.students
    }

    private fun checkTeacherSubjectFitsClassSubject(newClass: SchoolClassInputDTO) {
        val teacher = teacherRepository.findByIdOrNull(newClass.teacherId)
        if (teacher?.subject != newClass.subject) throw Exception("Teacher and Class subject do not match")
    }

}
