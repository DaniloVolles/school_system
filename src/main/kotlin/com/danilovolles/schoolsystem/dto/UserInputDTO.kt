package com.danilovolles.schoolsystem.dto

import jakarta.validation.constraints.NotBlank

data class UserInputDTO(

    @NotBlank(message = "Name is a required field")
    val name: String,

    @NotBlank(message = "Email is a required field")
    val email: String,

)
