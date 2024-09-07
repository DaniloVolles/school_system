package com.danilovolles.schoolsystem.handler

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.ApiResponseStatus
import com.danilovolles.schoolsystem.exception.UserAlreadyExistsException
import com.danilovolles.schoolsystem.exception.UserNotFoundException
import com.danilovolles.schoolsystem.exception.UserServiceLogicException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserServiceExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun userNotFoundExceptionHandler(exception: UserNotFoundException): ResponseEntity<ApiResponseDTO<*>> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponseDTO(ApiResponseStatus.FAIL.name, exception))
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun userAlreadyExistsExceptionHandler(exception: UserAlreadyExistsException): ResponseEntity<ApiResponseDTO<*>> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiResponseDTO(ApiResponseStatus.FAIL.name, exception))
    }

    @ExceptionHandler(UserServiceLogicException::class)
    fun userServiceLogicExceptionHandler(exception: UserServiceLogicException): ResponseEntity<ApiResponseDTO<*>> {
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
