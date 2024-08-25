package com.danilovolles.schoolsystem.dto

data class ApiResponseDTO<Any>(
    val status: String,
    val response: Any
)
