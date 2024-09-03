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
                .status(HttpStatus.OK)
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
            val schoolClass = schoolClassRepository.getSchoolClassById(classId) ?: throw Exception("Class not found")

//            val currentStudents = schoolClass.students ?: mutableSetOf()
            val currentStudentsNumber = schoolClass.students?.size ?: 0

            val currentStudentsOther = this.findStudentsBySchoolClass(classId)
            val studentsToAdd = studentRepository.findAllById(studentsIds.students).toSet()

            val totalStudentCount = currentStudentsNumber + studentsToAdd.size

            if (totalStudentCount > 10) throw Exception("Student count mustn't exceed 10 per class")

            currentStudentsOther?.addAll(studentsToAdd)
            schoolClass.students = currentStudentsOther

            schoolClassRepository.save(schoolClass)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Students added successfully"))

        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }

    override fun inactiveClass(classId: Long): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val schoolClass = schoolClassRepository
                .findById(classId)
                .orElseThrow { Exception("Class not found with this ID") }

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

    private fun verifyIfSchoolClassExists(schoolClass: SchoolClassInputDTO): SchoolClass? {
        val bySubject = schoolClassRepository.getSchoolClassBySubject(schoolClass.subject)
        val byName = schoolClassRepository.getSchoolClassByName(schoolClass.name)

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

    private fun findStudentsByIdSet(studentsIds: Set<UUID>?): MutableSet<Student> {
        if (studentsIds == null) {
            throw Exception("student set is null")
        }
        return studentRepository.findAllById(studentsIds).toMutableSet()
    }

    private fun checkNumberOfStudents(students: Set<Student>) {
        if (students.count() > 10) {
            throw Exception("Classes must not have more than 30 students")
        }
    }

    private fun findStudentsBySchoolClass(schoolClassId: Long): MutableSet<Student>? {
        val schoolClass = schoolClassRepository.getSchoolClassById(schoolClassId) ?: throw Exception("Class not found")
        return schoolClass.students
    }



}
