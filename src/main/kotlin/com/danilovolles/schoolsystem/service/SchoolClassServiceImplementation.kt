package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.*
import com.danilovolles.schoolsystem.entity.SchoolClass
import com.danilovolles.schoolsystem.entity.Student
import com.danilovolles.schoolsystem.entity.Teacher
import com.danilovolles.schoolsystem.repository.SchoolClassRepository
import com.danilovolles.schoolsystem.repository.StudentRepository
import com.danilovolles.schoolsystem.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*
import kotlin.math.E

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

    override fun insertStudent(studentsIds: InsertStudentSetInClassDTO, classId: Long): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val schoolClass = schoolClassRepository
                .getSchoolClassById(classId) ?: throw Exception("Class not found")

            val students = studentRepository.findAllById(studentsIds.students).toMutableSet()

            val updatedStudents =  schoolClass.students?.let {
                val combinedStudents = it + students
                checkNumberOfStudents(combinedStudents)
                combinedStudents.toMutableSet()
            } ?: students

            schoolClass.students = updatedStudents

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

}