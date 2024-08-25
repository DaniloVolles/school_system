package com.danilovolles.schoolsystem.repository

import com.danilovolles.schoolsystem.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findUserByEmail(email: String): User?
}