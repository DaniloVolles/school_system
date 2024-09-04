package com.danilovolles.schoolsystem.repository

import com.danilovolles.schoolsystem.entity.SchoolClass
import com.danilovolles.schoolsystem.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface SchoolClassRepository : JpaRepository<SchoolClass, Long> {
    fun findSchoolClassBySubject(subject: String): SchoolClass?
    fun findSchoolClassByName(name: String): SchoolClass?
    fun findSchoolClassById(id: Long): SchoolClass?
    fun findSchoolClassByTeacherId(teacherId: UUID): SchoolClass?

    @Query(/* value = TODO() */)
    fun findStudentsBySchoolClass(): Set<Student>?
}
