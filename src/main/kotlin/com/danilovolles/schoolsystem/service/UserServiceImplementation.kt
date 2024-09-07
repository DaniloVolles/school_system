package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.ApiResponseDTO
import com.danilovolles.schoolsystem.dto.ApiResponseStatus
import com.danilovolles.schoolsystem.dto.UserInputDTO
import com.danilovolles.schoolsystem.dto.UserOutputDTO
import com.danilovolles.schoolsystem.entity.User
import com.danilovolles.schoolsystem.exception.UserAlreadyExistsException
import com.danilovolles.schoolsystem.exception.UserNotFoundException
import com.danilovolles.schoolsystem.exception.UserServiceLogicException
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
                password = UUID.randomUUID().toString(),
            )

            userRepository.save(savingUser)

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "New user created successfully"))

        } catch (e: UserAlreadyExistsException) {
            throw UserAlreadyExistsException(e.localizedMessage)
        } catch (e: Exception) {
            e.stackTrace
            throw UserServiceLogicException(e.localizedMessage)
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
            throw UserServiceLogicException(e.localizedMessage)
        }
    }

    override fun getUserById(userId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            val user = userRepository
                .findById(userId)
                .orElseThrow { RuntimeException("User not found in our database") }

            val userOutput = userToUserOutput(user.name, user.email)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, userOutput))

        } catch (e: UserNotFoundException) {
            throw UserNotFoundException(e.localizedMessage)
        } catch (e: Exception) {
            e.stackTrace
            throw UserServiceLogicException(e.localizedMessage)
        }

    }

    override fun updateUser(userId: UUID, userUpdate: UserInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val user = userRepository
                .findById(userId)
                .orElseThrow { RuntimeException("User not found in our database") }

            user.name = userUpdate.name
            user.email = userUpdate.email

            userRepository.save(user)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "User updated successfully"))
        } catch (e: UserNotFoundException) {
            throw UserNotFoundException(e.localizedMessage)
        } catch (e: Exception) {
            e.stackTrace
            throw UserServiceLogicException(e.localizedMessage)
        }
    }

    override fun deactivateUser(userId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val user = userRepository
                .findById(userId)
                .orElseThrow { RuntimeException("User not found in our database") }

            user.active = false

            userRepository.save(user)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "User inactivated successfully"))

        } catch (e: UserNotFoundException) {
            throw UserNotFoundException(e.localizedMessage)
        } catch (e: Exception) {
            e.stackTrace
            throw UserServiceLogicException(e.localizedMessage)
        }
    }

    private fun userToUserOutput(name: String, email: String) = UserOutputDTO(name = name, email = email)

    private fun verifyUserExists(userDto: UserInputDTO): User? {
        val user = userRepository.findUserByEmail(userDto.email)
        if (user != null) {
            throw UserAlreadyExistsException("User already in our database")
        }
        return null
    }

}