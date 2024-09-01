package com.danilovolles.schoolsystem.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class StudentInputDTO(
    @NotBlank(message = "Name must be provided")
    var name: String,

    @NotBlank(message = "Email must be provided")
    @Email(message = "Email must be valid")
    var email: String,

)
