package com.danilovolles.schoolsystem.dto

import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class SchoolClassInputDTO(

    @NotBlank(message = "Name must be provided")
    var name: String,

    @NotBlank(message = "Description must be provided")
    var description: String,

    @NotBlank(message = "Subject must be provided")
    var subject: String,

    var teacherId: UUID
)
