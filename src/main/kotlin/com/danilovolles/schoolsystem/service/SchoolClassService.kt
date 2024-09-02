package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.InsertStudentSetInClassDTO
import com.danilovolles.schoolsystem.dto.SchoolClassInputDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.Set

@Service
interface SchoolClassService {
    fun createClass(newClass: SchoolClassInputDTO): ResponseEntity<ApiResponseDTO<Any>>
    fun getAllClasses(): ResponseEntity<ApiResponseDTO<Any?>>
    fun insertStudent(studentsIds: InsertStudentSetInClassDTO, classId: Long): ResponseEntity<ApiResponseDTO<Any>>
    fun inactiveClass(classId: Long): ResponseEntity<ApiResponseDTO<Any>>
}