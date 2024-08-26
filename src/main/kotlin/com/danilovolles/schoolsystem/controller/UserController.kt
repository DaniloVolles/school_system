package com.danilovolles.schoolsystem.controller

import com.danilovolles.schoolsystem.dto.UserInputDTO
import com.danilovolles.schoolsystem.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping("/new")
    fun createUser(@Valid @RequestBody userData: UserInputDTO) = userService.createUser(userData)

    @GetMapping("/get/all")
    fun getAllUsers() = userService.getAllUsers()

    @GetMapping("/get/{userId}")
    fun getUserById(@Valid @PathVariable userId: UUID) = userService.getUserById(userId)

    @PutMapping("/update/{userId}")
    fun updateUserById(
        @Valid @PathVariable userId: UUID,
        @RequestBody userData: UserInputDTO
    ) = userService.updateUser(userId, userData)

    @PutMapping("/inactive/{userId}")
    fun inactiveUserById(@Valid @PathVariable userId: UUID) = userService.inactiveUser(userId)

}
