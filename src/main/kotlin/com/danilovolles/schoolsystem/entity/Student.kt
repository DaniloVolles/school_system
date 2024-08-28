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

    @ManyToMany(mappedBy = "students")
    var schoolClasses: Set<SchoolClass> = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name = "tb_student_teacher",
        joinColumns = [JoinColumn(name = "student_id")],
        inverseJoinColumns = [JoinColumn(name = "teacher_id")]
    )
    var teachers: Set<Teacher> = mutableSetOf()
)