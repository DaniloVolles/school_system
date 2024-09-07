package com.danilovolles.schoolsystem.handler

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.ApiResponseStatus
import com.danilovolles.schoolsystem.exception.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler

class SchoolClassServiceExceptionHandler {

    @ExceptionHandler(SchoolClassNotFoundException::class)
    fun teacherNotFoundExceptionHandler(exception: SchoolClassNotFoundException): ResponseEntity<ApiResponseDTO<*>> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponseDTO(ApiResponseStatus.FAIL.name, exception))
    }

    @ExceptionHandler(SchoolClassAlreadyExistsException::class)
    fun teacherAlreadyExistsExceptionHandler(exception: SchoolClassAlreadyExistsException): ResponseEntity<ApiResponseDTO<*>> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponseDTO(ApiResponseStatus.FAIL.name, exception))
    }

    @ExceptionHandler(SchoolClassServiceLogicException::class)
    fun teacherServiceLogicExceptionHandler(exception: SchoolClassServiceLogicException): ResponseEntity<ApiResponseDTO<*>> {
        return ResponseEntity
            .badRequest()
            .body(ApiResponseDTO(ApiResponseStatus.FAIL.name, exception))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(exception: MethodArgumentNotValidException): ResponseEntity<ApiResponseDTO<*>> {
        val errorMessage = exception
            .bindingResult
            .fieldErrors
            .mapNotNull { it.defaultMessage }

        return ResponseEntity
            .badRequest()
            .body(ApiResponseDTO(ApiResponseStatus.FAIL.name, exception))
    }
}