package com.danilovolles.schoolsystem.entity

import jakarta.persistence.*

@Entity
@Table(name = "tb_schoolclasses")
data class SchoolClass (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    var name: String,
    var description: String,
    var subject: String,

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    var teacher: Teacher?,

    @ManyToMany
    @JoinTable(
        name = "tb_student_schoolclass",
        joinColumns = [JoinColumn(name = "schoolclass_id")],
        inverseJoinColumns = [JoinColumn(name = "student_id")]
    )
    var students: Set<Student>? = mutableSetOf()


)