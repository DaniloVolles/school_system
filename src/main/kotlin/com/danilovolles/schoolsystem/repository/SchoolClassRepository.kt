package com.danilovolles.schoolsystem.repository

import com.danilovolles.schoolsystem.entity.SchoolClass
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SchoolClassRepository : JpaRepository<SchoolClass, Long> {
    fun findSchoolClassBySubject(subject: String): SchoolClass?
    fun findSchoolClassByName(name: String): SchoolClass?
    fun findSchoolClassById(id: Long): SchoolClass?
    fun findSchoolClassByTeacherId(teacherId: UUID): SchoolClass?

//    @Query(/* value = TODO() */)
//    fun findStudentsBySchoolClass(): Set<Student>? = TODO()
}
