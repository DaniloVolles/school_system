package com.danilovolles.schoolsystem.handler

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.ApiResponseStatus
import com.danilovolles.schoolsystem.exception.StudentAlreadyExistsException
import com.danilovolles.schoolsystem.exception.StudentNotFoundException
import com.danilovolles.schoolsystem.exception.StudentServiceLogicException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler

class StudentServiceExceptionHandler {

    @ExceptionHandler(StudentNotFoundException::class)
    fun teacherNotFoundExceptionHandler(exception: StudentNotFoundException): ResponseEntity<ApiResponseDTO<*>> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponseDTO(ApiResponseStatus.FAIL.name, exception))
    }

    @ExceptionHandler(StudentAlreadyExistsException::class)
    fun teacherAlreadyExistsExceptionHandler(exception: StudentAlreadyExistsException): ResponseEntity<ApiResponseDTO<*>> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponseDTO(ApiResponseStatus.FAIL.name, exception))
    }

    @ExceptionHandler(StudentServiceLogicException::class)
    fun teacherServiceLogicExceptionHandler(exception: StudentServiceLogicException): ResponseEntity<ApiResponseDTO<*>> {
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