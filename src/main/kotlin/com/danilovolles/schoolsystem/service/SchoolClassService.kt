package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.SchoolClassInputDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.Set

@Service
interface SchoolClassService {
    fun createClass(newClass: SchoolClassInputDTO): ResponseEntity<ApiResponseDTO<Any>>
    fun getAllClasses(): ResponseEntity<ApiResponseDTO<Any>>
    fun getClassesByTeacher(teacherId: UUID): ResponseEntity<ApiResponseDTO<Any>>
    fun getClassesByStudent(studentId: UUID): ResponseEntity<ApiResponseDTO<Any>>
    fun insertTeacher(teacherId: UUID): ResponseEntity<ApiResponseDTO<Any>>
    fun insertStudent(studentsIds: Set<UUID>): ResponseEntity<ApiResponseDTO<Any>>
    fun inactiveClass(classId: UUID): ResponseEntity<ApiResponseDTO<Any>>
}