package com.danilovolles.schoolsystem.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigInteger
import java.util.UUID

@Entity
@Table(name = "tb_users")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    val id: UUID?,

    val email: String,
    val name: String,
    val password: String
)
