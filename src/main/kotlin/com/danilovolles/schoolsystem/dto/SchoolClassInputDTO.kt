package com.danilovolles.schoolsystem.dto

import com.danilovolles.schoolsystem.entity.Teacher
import jakarta.validation.constraints.NotBlank

data class SchoolClassInputDTO(

    @NotBlank(message = "Name must be provided")
    var name: String,

    @NotBlank(message = "Description must be provided")
    var description: String,

    @NotBlank(message = "Subject must be provided")
    var subject: String,

    var teacher: Teacher?
)
