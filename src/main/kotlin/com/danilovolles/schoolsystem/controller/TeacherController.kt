package com.danilovolles.schoolsystem.controller

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.TeacherInputDTO
import com.danilovolles.schoolsystem.dto.UserInputDTO
import com.danilovolles.schoolsystem.service.TeacherService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/teachers")
class TeacherController {

    @Autowired
    final lateinit var teacherService: TeacherService

    @PostMapping("/new")
    fun createTeacher(@Valid @RequestBody teacherData: TeacherInputDTO) = teacherService.createTeacher(teacherData)

    @GetMapping("/get/all")
    fun getAllTeachers() = teacherService.getAllTeacher()

    @GetMapping("/get/{teacherId}")
    fun getTeacherById(@PathVariable teacherId: UUID) = teacherService.getTeacherById(teacherId)

    @GetMapping("/get/subj/{subjectName}")
    fun getTeacherBySubject(@PathVariable subjectName: String) = teacherService.getTeacherBySubject(subjectName)

    @PutMapping("/update/{teacherId}")
    fun updateTeacherById(
        @PathVariable teacherId: UUID,
        @RequestBody teacherData: TeacherInputDTO
    ) = teacherService.updateTeacher(teacherId, teacherData)

    @PutMapping("/inactive/{teacherId}")
    fun inactiveTeacherById(@PathVariable teacherId: UUID) = teacherService.deactivateTeacher(teacherId)
}