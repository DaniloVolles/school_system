package com.danilovolles.schoolsystem.dto

import jakarta.validation.constraints.NotBlank

data class StudentInputDTO(
    @NotBlank(message = "Name must be provided")
    var name: String,

    @NotBlank(message = "Email must be provided")
    var email: String,

    @NotBlank(message = "Class must be provided")
    var schoolClass: String
)
