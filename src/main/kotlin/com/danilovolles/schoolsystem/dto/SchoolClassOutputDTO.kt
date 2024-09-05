package com.danilovolles.schoolsystem.dto

data class SchoolClassOutputDTO (
    var name: String,
    var description: String,
    var subject: String,
    var teacher: TeacherOutputDTO?
)