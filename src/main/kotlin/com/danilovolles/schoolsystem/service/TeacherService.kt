package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.TeacherInputDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
interface TeacherService {
    fun createTeacher(newTeacher: TeacherInputDTO): ResponseEntity<ApiResponseDTO<Any>>
    fun getAllTeacher(): ResponseEntity<ApiResponseDTO<Any>>
    fun getTeacherById(teacherId: UUID): ResponseEntity<ApiResponseDTO<Any>>
    fun getTeacherBySubject(subject: String): ResponseEntity<ApiResponseDTO<Any>>
    fun updateTeacher(teacherId: UUID, teacherUpdate: TeacherInputDTO): ResponseEntity<ApiResponseDTO<Any>>
    fun inactiveTeacher(teacherId: UUID): ResponseEntity<ApiResponseDTO<Any>>
}