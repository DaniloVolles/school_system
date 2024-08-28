package com.danilovolles.schoolsystem.repository

import com.danilovolles.schoolsystem.entity.SchoolClass
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SchoolClassRepository : JpaRepository<SchoolClass, Long> {
    fun getSchoolClassByStudent(studentId: UUID): SchoolClass?
    fun getSchoolClassByTeacher(teacherId: UUID): SchoolClass?
    fun getSchoolClassBySubject(subject: String): SchoolClass?
    fun getSchoolClassByName(name: String): SchoolClass?
}
