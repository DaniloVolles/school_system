package com.danilovolles.schoolsystem.dto

import java.util.*

data class StudentOutputDTO(
    val id: UUID,
    var name: String,
    var email: String,
    var active: Boolean
)
