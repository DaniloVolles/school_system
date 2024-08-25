package com.danilovolles.schoolsystem.controller

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.UserInputDTO
import com.danilovolles.schoolsystem.service.UserService
import jakarta.persistence.criteria.CriteriaBuilder.In
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/new")
    fun createUser(@Valid @RequestBody userData: UserInputDTO) = userService.createUser(userData)


}
