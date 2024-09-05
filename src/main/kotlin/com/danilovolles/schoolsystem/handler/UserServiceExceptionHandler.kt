package com.danilovolles.schoolsystem.handler

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.ApiResponseStatus
import com.danilovolles.schoolsystem.exception.UserAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserServiceExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun userAlreadyExistsExceptionHandler(exception: UserAlreadyExistsException): ResponseEntity<ApiResponseDTO<*>> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponseDTO(ApiResponseStatus.FAIL.name, exception))
    }
}