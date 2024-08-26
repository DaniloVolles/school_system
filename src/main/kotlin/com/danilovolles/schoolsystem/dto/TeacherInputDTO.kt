package com.danilovolles.schoolsystem.dto

import jakarta.validation.constraints.NotBlank

data class TeacherInputDTO (

    @NotBlank(message = "Name must be provided")
    var name: String,

    @NotBlank(message = "Email must be provided")
    var email: String,

    @NotBlank(message = "Subject must be provided")
    var subject: String,
)