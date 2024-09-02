package com.danilovolles.schoolsystem.repository

import com.danilovolles.schoolsystem.entity.SchoolClass
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SchoolClassRepository : JpaRepository<SchoolClass, Long> {
    fun getSchoolClassBySubject(subject: String): SchoolClass?
    fun getSchoolClassByName(name: String): SchoolClass?
    fun getSchoolClassById(id: Long): SchoolClass?
    fun getSchoolClassByTeacherId(teacherId: UUID): SchoolClass?
}
