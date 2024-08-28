package com.danilovolles.schoolsystem.dto

import com.danilovolles.schoolsystem.entity.Teacher

data class SchoolClassOutputDTO (
    var name: String,
    var description: String,
    var subject: String,
    var teacher: Teacher?
)