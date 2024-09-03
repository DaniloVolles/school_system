package com.danilovolles.schoolsystem.repository

import com.danilovolles.schoolsystem.entity.SchoolClass
import com.danilovolles.schoolsystem.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface SchoolClassRepository : JpaRepository<SchoolClass, Long> {
    fun getSchoolClassBySubject(subject: String): SchoolClass?
    fun getSchoolClassByName(name: String): SchoolClass?
    fun getSchoolClassById(id: Long): SchoolClass?
    fun getSchoolClassByTeacherId(teacherId: UUID): SchoolClass?

    @Query("SELECT students FROM ")
    fun xpto(@Param("id") Long id): Set<Student>?
}
