package com.danilovolles.schoolsystem.dto

import com.danilovolles.schoolsystem.entity.Teacher

data class ClassInputDTO(
    var name: String,
    var description: String,
    var teacher: Teacher?
)
