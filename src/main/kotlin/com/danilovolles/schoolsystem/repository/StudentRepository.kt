package com.danilovolles.schoolsystem.repository

import com.danilovolles.schoolsystem.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StudentRepository : JpaRepository<Student, UUID> {
    fun findStudentByName(name: String): Student?
}