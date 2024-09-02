package com.danilovolles.schoolsystem.dto

import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class InsertStudentSetInClassDTO(
    @NotBlank
    var students: Set<UUID> = emptySet()
)
