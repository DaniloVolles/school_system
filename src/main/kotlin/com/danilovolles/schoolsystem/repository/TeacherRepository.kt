package com.danilovolles.schoolsystem.repository

import com.danilovolles.schoolsystem.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TeacherRepository : JpaRepository<Teacher, UUID> {
    fun findTeacherByEmail(email: String): Teacher?
}