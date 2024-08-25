package com.danilovolles.schoolsystem.service

import ch.qos.logback.core.status.WarnStatus
import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.ApiResponseStatus
import com.danilovolles.schoolsystem.dto.UserInputDTO
import com.danilovolles.schoolsystem.dto.UserOutputDTO
import com.danilovolles.schoolsystem.entity.User
import com.danilovolles.schoolsystem.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserServiceImplementation : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun createUser(newUser: UserInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            this.verifyUserExists(newUser)

            val savingUser = User(
                id = null,
                name = newUser.name,
                email = newUser.email,
                password = UUID.randomUUID().toString()
            )

            userRepository.save(savingUser)

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "New user created successfully"))

        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }

    override fun getAllUsers(): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val users: List<User> = userRepository.findAll()
            val userList: MutableList<UserOutputDTO> = mutableListOf()

            for (user in users) {
                val userOutput = UserOutputDTO(name = user.name, email = user.email)
                userList.add(userOutput)
            }

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, userList))
        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }

    private fun verifyUserExists(userDto: UserInputDTO): User? {
        val user = userRepository.findUserByEmail(userDto.email)
        if (user != null) {
            throw Exception("User already in our database")
        }
        return user
    }

}