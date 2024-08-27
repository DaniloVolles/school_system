package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.*
import com.danilovolles.schoolsystem.entity.Student
import com.danilovolles.schoolsystem.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class StudentServiceImplementation : StudentService {
    
    @Autowired
    private lateinit var studentRepository: StudentRepository
    
    override fun createStudent(newStudent: StudentInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            this.verifyStudentExists(newStudent)

            val savingStudent = Student(
                id = null,
                name = newStudent.name,
                email = newStudent.email,
                password = UUID.randomUUID().toString(),
                schoolClass = newStudent.schoolClass,
                active = true,
            )

            studentRepository.save(savingStudent)

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "New student created successfully"))

        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }

    override fun getAllStudent(): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val students: List<Student> = studentRepository.findAll()
            val studentList: MutableList<StudentOutputDTO> = mutableListOf()

            for (student in students) {

                val studentOutput = StudentOutputDTO(
                    name = student.name,
                    schoolClass = student.schoolClass,
                    active = student.active
                )

                studentList.add(studentOutput)
            }

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, studentList))
        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }

    override fun getStudentById(studentId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            val student = studentRepository
                .findById(studentId)
                .orElseThrow { RuntimeException("Student not found in our database") }

            val studentOutput = studentToStudentOutput(student)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, studentOutput))

        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }

    override fun getStudentByClass(schoolClass: String): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            val student = studentRepository.findStudentBySchoolClass(schoolClass)
                ?: throw Exception("Student not found in our database")

            val studentOutput = studentToStudentOutput(student)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, studentOutput))

        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }


    override fun updateStudent(studentId: UUID, studentUpdate: StudentInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val student = studentRepository
                .findById(studentId)
                .orElseThrow { RuntimeException("Student not found in our database") }

            student.name = studentUpdate.name
            student.email = studentUpdate.email
            student.schoolClass = studentUpdate.schoolClass

            studentRepository.save(student)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Student updated successfully"))
        } catch (e: Exception){
            e.stackTrace
            throw Exception(e.message)
        }
    }

    override fun inactiveStudent(studentId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            val student = studentRepository
                .findById(studentId)
                .orElseThrow { RuntimeException("Student not found in our database") }
            student.active = false

            studentRepository.save(student)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Student inactivated successfully"))
        } catch (e: Exception){
            e.stackTrace
            throw Exception(e.message)
        }
    }

    private fun studentToStudentOutput(student: Student): StudentOutputDTO {
        return StudentOutputDTO(
            name = student.name,
            schoolClass = student.schoolClass,
            active = student.active
        )
    }

    private fun verifyStudentExists(studentDto: StudentInputDTO): Student? {
        val student = studentRepository.findStudentByName(studentDto.name)
        if (student != null) {
            throw Exception("Student already in our database")
        } else{
            return student
        }
    }
    
}