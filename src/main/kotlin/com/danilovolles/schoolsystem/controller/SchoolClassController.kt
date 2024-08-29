package com.danilovolles.schoolsystem.controller

import com.danilovolles.schoolsystem.dto.SchoolClassInputDTO
import com.danilovolles.schoolsystem.service.SchoolClassService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/classes")
class SchoolClassController {

    @Autowired
    private lateinit var schoolClassService: SchoolClassService

    @PostMapping("/new")
    fun createNewClass(@Valid @RequestBody classInput: SchoolClassInputDTO) = schoolClassService.createClass(classInput)

    @GetMapping("/get/all")
    fun getAllClasses() = schoolClassService.getAllClasses()

}