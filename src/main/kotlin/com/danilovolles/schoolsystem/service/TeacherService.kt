package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.UserInputDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
interface TeacherService {
    fun createTeacher(newUser: UserInputDTO): ResponseEntity<ApiResponseDTO<Any>>
    fun getAllTeacher(): ResponseEntity<ApiResponseDTO<Any>>
    fun getTeacherById(userId: UUID): ResponseEntity<ApiResponseDTO<Any>>
    fun getTeacherBySubject(subject: String): ResponseEntity<ApiResponseDTO<Any>>
    fun updateTeacher(userId: UUID, userUpdate: UserInputDTO): ResponseEntity<ApiResponseDTO<Any>>
    fun inactiveTeacher(userId: UUID): ResponseEntity<ApiResponseDTO<Any>>
}