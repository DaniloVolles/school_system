package com.danilovolles.schoolsystem.controller

import com.danilovolles.schoolsystem.dto.StudentInputDTO
import com.danilovolles.schoolsystem.service.StudentService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/students")
class StudentController {

    @Autowired
    final lateinit var studentService: StudentService

    @PostMapping("/new")
    fun createStudent(@Valid @RequestBody studentData: StudentInputDTO) = studentService.createStudent(studentData)

    @GetMapping("/get/all")
    fun getAllStudents() = studentService.getAllStudent()

    @GetMapping("/get/{studentId}")
    fun getStudentById(@Valid @PathVariable studentId: UUID) = studentService.getStudentById(studentId)

    @PutMapping("/update/{studentId}")
    fun updateStudentById(
        @Valid @PathVariable studentId: UUID,
        @RequestBody studentData: StudentInputDTO
    ) = studentService.updateStudent(studentId, studentData)

    @PutMapping("/inactive/{studentId}")
    fun inactiveStudentById(@Valid @PathVariable studentId: UUID) = studentService.inactiveStudent(studentId)
}