package com.danilovolles.schoolsystem.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "tb_student")
data class Student(

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    var name: String,
    var email: String,
    var password: String,
    var active: Boolean,
    var schoolClass: String
)