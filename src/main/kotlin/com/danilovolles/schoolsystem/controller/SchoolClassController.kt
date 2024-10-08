package com.danilovolles.schoolsystem.controller

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.InsertStudentSetInClassDTO
import com.danilovolles.schoolsystem.dto.SchoolClassInputDTO
import com.danilovolles.schoolsystem.service.SchoolClassService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
@RequestMapping("/classes")
class SchoolClassController {

    @Autowired
    private lateinit var schoolClassService: SchoolClassService

    @PostMapping("/new")
    fun createNewClass(@Valid @RequestBody classInput: SchoolClassInputDTO) = schoolClassService.createClass(classInput)

    @GetMapping("/get/all")
    fun getAllClasses() = schoolClassService.getAllClasses()

    @PutMapping("/inactive/{classId}")
    fun inactiveClass(@PathVariable classId: Long) = schoolClassService.deactivateClass(classId)

    @PutMapping("/insert/student/inclass/{classId}")
    fun insertNewStudents(
        @RequestBody students: InsertStudentSetInClassDTO,
        @PathVariable classId: Long
    ): ResponseEntity<ApiResponseDTO<Any>> {
        return schoolClassService.insertStudent(students, classId)
    }
}