package com.danilovolles.schoolsystem.repository

import com.danilovolles.schoolsystem.entity.SchoolClass
import com.danilovolles.schoolsystem.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface SchoolClassRepository : JpaRepository<SchoolClass, Long> {
    fun findSchoolClassBySubject(subject: String): SchoolClass?
    fun findSchoolClassByName(name: String): SchoolClass?
    fun findSchoolClassById(id: Long): SchoolClass?
    fun findSchoolClassByTeacherId(teacherId: UUID): SchoolClass?

    @Query(
        value =  "SELECT s.* FROM tb_student s " +
                "JOIN tb_student_schoolclass sc " +
                "ON s.id = sc.student_id " +
                "WHERE sc.schoolclass_id  = :id",
        nativeQuery = true
    )
    fun findStudentsBySchoolClass(@Param("id") id: Long): MutableSet<Any>
}
