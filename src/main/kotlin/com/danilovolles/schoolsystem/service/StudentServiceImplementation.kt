package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.StudentInputDTO
import org.springframework.http.ResponseEntity
import java.util.*

class StudentServiceImplementation : StudentService {
    override fun createStudent(newStudent: StudentInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun getAllStudent(): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun getStudentById(studentId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun getStudentByClass(schoolClass: String): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }


    override fun updateStudent(studentId: UUID, studentUpdate: StudentInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun inactiveStudent(studentId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }
}