package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.StudentInputDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
interface StudentService {
    fun createStudent(newStudent: StudentInputDTO): ResponseEntity<ApiResponseDTO<Any>>
    fun getAllStudent(): ResponseEntity<ApiResponseDTO<Any>>
    fun getStudentById(studentId: UUID): ResponseEntity<ApiResponseDTO<Any>>
    fun getStudentByClass(schoolClass: String): ResponseEntity<ApiResponseDTO<Any>>
    fun updateStudent(studentId: UUID, studentUpdate: StudentInputDTO): ResponseEntity<ApiResponseDTO<Any>>
    fun inactiveStudent(studentId: UUID): ResponseEntity<ApiResponseDTO<Any>>
}