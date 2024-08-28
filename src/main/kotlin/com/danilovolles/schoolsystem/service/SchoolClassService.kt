package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.ClassInputDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
interface SchoolClassService {
    fun createClass(newClass: ClassInputDTO): ResponseEntity<ApiResponseDTO<Any>>
    fun getAllClasses(): ResponseEntity<ApiResponseDTO<Any>>
    fun getClassById(classId: UUID): ResponseEntity<ApiResponseDTO<Any>>
    fun getClassesByTeacher(teacherId: UUID): ResponseEntity<ApiResponseDTO<Any>>
    fun getClassesByStudent(studentId: UUID): ResponseEntity<ApiResponseDTO<Any>>
    fun updateClass(classId: UUID, classUpdate: ClassInputDTO): ResponseEntity<ApiResponseDTO<Any>>
    fun inactiveClass(classId: UUID): ResponseEntity<ApiResponseDTO<Any>>
}