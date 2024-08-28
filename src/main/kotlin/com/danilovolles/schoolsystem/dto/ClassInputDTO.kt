package com.danilovolles.schoolsystem.dto

import com.danilovolles.schoolsystem.entity.Teacher
import java.util.UUID

data class ClassInputDTO(
    var name: String,
    var description: String,
    var teacher: Teacher?,
    var student: Set<UUID>
)
