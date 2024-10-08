package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.*
import com.danilovolles.schoolsystem.entity.Student
import com.danilovolles.schoolsystem.exception.*
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
                schoolClasses = null,
                active = true,
            )

            studentRepository.save(savingStudent)

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "New student created successfully"))

        } catch (e: StudentNotFoundException){
            e.stackTrace
            throw StudentNotFoundException(e.localizedMessage)

        } catch (e: Exception){
            e.stackTrace
            throw StudentServiceLogicException(e.localizedMessage)
        }
    }

    override fun getAllStudent(): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val students: List<Student> = studentRepository.findAll()
            val studentList: MutableList<StudentOutputDTO> = mutableListOf()

            for (student in students) {

                val studentOutput = studentToStudentOutput(student)

                if (studentOutput != null) {
                    studentList.add(studentOutput)
                }
            }

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, studentList))
        } catch (e: Exception) {
            e.stackTrace
            throw TeacherServiceLogicException(e.localizedMessage)
        }
    }

    override fun getStudentById(studentId: UUID): ResponseEntity<ApiResponseDTO<Any?>> {
        try {

            val student = studentRepository
                .findById(studentId)
                .orElseThrow { StudentNotFoundException("Student not found in our database") }

            val studentOutput = studentToStudentOutput(student)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, studentOutput))

        } catch (e: StudentNotFoundException) {
            e.stackTrace
            throw StudentNotFoundException(e.localizedMessage)

        } catch (e: Exception) {
            e.stackTrace
            throw StudentServiceLogicException(e.localizedMessage)
        }
    }

    override fun updateStudent(studentId: UUID, studentUpdate: StudentInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val student = studentRepository
                .findById(studentId)
                .orElseThrow { RuntimeException("Student not found in our database") }

            student.name = studentUpdate.name
            student.email = studentUpdate.email

            studentRepository.save(student)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Student updated successfully"))

        } catch (e: StudentNotFoundException){
            e.stackTrace
            throw StudentNotFoundException(e.localizedMessage)

        } catch (e: Exception){
            e.stackTrace
            throw StudentServiceLogicException(e.localizedMessage)
        }
    }

    override fun deactivateStudent(studentId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            val student = studentRepository
                .findById(studentId)
                .orElseThrow { RuntimeException("Student not found in our database") }
            student.active = false

            studentRepository.save(student)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Student inactivated successfully"))

        } catch (e: StudentNotFoundException){
            e.stackTrace
            throw StudentNotFoundException(e.localizedMessage)

        } catch (e: Exception){
            e.stackTrace
            throw StudentServiceLogicException(e.localizedMessage)
        }
    }

    private fun studentToStudentOutput(student: Student): StudentOutputDTO? {
        return student.id?.let {
            StudentOutputDTO(
                id = it,
                name = student.name,
                email = student.email,
                active = student.active,
            )
        }
    }

    private fun verifyStudentExists(studentDto: StudentInputDTO): Student? {
        val student = studentRepository.findStudentByName(studentDto.name)
        if (student != null) {
            throw StudentAlreadyExistsException("Student already in our database")
        }
        return null
    }
    
}