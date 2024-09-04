package com.danilovolles.schoolsystem.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "tb_teachers")
data class Teacher(

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    var name: String,
    var email: String,
    var password: String,
    var subject: String,
    var active: Boolean,

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    var schoolClasses: MutableSet<SchoolClass>
    
)
