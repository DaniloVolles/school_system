package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.UserInputDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.UUID

@Service
interface UserService {

    fun createUser(newUser: UserInputDTO): ResponseEntity<ApiResponseDTO<Any>>
    fun getAllUsers(): ResponseEntity<ApiResponseDTO<Any>>
    fun getUserById(userId: UUID): ResponseEntity<ApiResponseDTO<Any>>

}