package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.UserInputDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class TeacherServiceImplementation : TeacherService {
    override fun createTeacher(newUser: UserInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun getAllTeacher(): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun getTeacherById(userId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun getTeacherBySubject(subject: String): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun updateTeacher(userId: UUID, userUpdate: UserInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun inactiveTeacher(userId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }


}