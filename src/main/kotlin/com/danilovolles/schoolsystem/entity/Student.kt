package com.danilovolles.schoolsystem.entity

import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
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

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    var schoolClasses: MutableSet<SchoolClass>?

)