package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.*
import com.danilovolles.schoolsystem.entity.Teacher
import com.danilovolles.schoolsystem.exception.TeacherAlreadyExistsException
import com.danilovolles.schoolsystem.exception.TeacherNotFoundException
import com.danilovolles.schoolsystem.exception.TeacherServiceLogicException
import com.danilovolles.schoolsystem.exception.UserServiceLogicException
import com.danilovolles.schoolsystem.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class TeacherServiceImplementation : TeacherService {

    @Autowired
    lateinit var teacherRepository: TeacherRepository

    override fun createTeacher(newTeacher: TeacherInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            this.verifyTeacherExists(newTeacher)

            val savingTeacher = Teacher(
                id = null,
                name = newTeacher.name,
                email = newTeacher.email,
                password = UUID.randomUUID().toString(),
                subject = newTeacher.subject,
                active = true,
                schoolClasses = null,
            )

            teacherRepository.save(savingTeacher)

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "New teacher created successfully"))

        } catch (e: TeacherAlreadyExistsException) {
            throw TeacherAlreadyExistsException(e.localizedMessage)
        } catch (e: Exception) {
            e.stackTrace
            throw TeacherServiceLogicException(e.localizedMessage)
        }
    }

    override fun getAllTeacher(): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val teachers: List<Teacher> = teacherRepository.findAll()
            val teacherList: MutableList<TeacherOutputDTO> = mutableListOf()

            for (teacher in teachers) {

                val teacherOutput = TeacherOutputDTO(
                    name = teacher.name,
                    email = teacher.email,
                    subject = teacher.subject,
                    active = teacher.active
                )

                teacherList.add(teacherOutput)
            }

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, teacherList))
        } catch (e: Exception) {
            e.stackTrace
            throw TeacherServiceLogicException(e.localizedMessage)
        }
    }

    override fun getTeacherById(teacherId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            val teacher = teacherRepository
                .findById(teacherId)
                .orElseThrow { RuntimeException("Teacher not found in our database") }

            val teacherOutput = teacherToTeacherOutput(teacher)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, teacherOutput))

        } catch (e: TeacherNotFoundException) {
            e.stackTrace
            throw TeacherNotFoundException(e.localizedMessage)
        } catch (e: Exception) {
            e.stackTrace
            throw TeacherServiceLogicException(e.localizedMessage)
        }
    }

    override fun getTeacherBySubject(subject: String): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            val teacher = teacherRepository.findTeacherBySubject(subject)
                ?: throw TeacherNotFoundException("Teacher not found in our database")

            val teacherOutput = teacherToTeacherOutput(teacher)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, teacherOutput))

        } catch (e: TeacherNotFoundException) {
            e.stackTrace
            throw TeacherNotFoundException(e.localizedMessage)

        } catch (e: Exception) {
            e.stackTrace
            throw TeacherServiceLogicException(e.localizedMessage)
        }
    }

    override fun updateTeacher(teacherId: UUID, teacherUpdate: TeacherInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        try {
            val teacher = teacherRepository
                .findById(teacherId)
                .orElseThrow { TeacherNotFoundException("Teacher not found in our database") }

            teacher.name = teacherUpdate.name
            teacher.email = teacherUpdate.email
            teacher.subject = teacherUpdate.subject

            teacherRepository.save(teacher)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Teacher updated successfully"))

        } catch (e: TeacherNotFoundException){
            e.stackTrace
            throw TeacherNotFoundException(e.localizedMessage)

        } catch (e: Exception){
            e.stackTrace
            throw TeacherServiceLogicException(e.localizedMessage)
        }
    }

    override fun deactivateTeacher(teacherId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            val teacher = teacherRepository
                .findById(teacherId)
                .orElseThrow { TeacherNotFoundException("Teacher not found in our database") }
            teacher.active = false

            teacherRepository.save(teacher)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Teacher inactivated successfully"))

        } catch (e: TeacherNotFoundException){
            e.stackTrace
            throw TeacherNotFoundException(e.localizedMessage)

        } catch (e: Exception){
            e.stackTrace
            throw TeacherServiceLogicException(e.localizedMessage)
        }
    }

    private fun teacherToTeacherOutput(teacher: Teacher): TeacherOutputDTO {
        return TeacherOutputDTO(
            name = teacher.name,
            email = teacher.email,
            subject = teacher.subject,
            active = teacher.active
        )
    }

    private fun verifyTeacherExists(teacherDto: TeacherInputDTO): Teacher? {
        val teacher = teacherRepository.findTeacherByEmail(teacherDto.email)
        if (teacher != null) {
            throw Exception("Teacher already in our database")
        }
        return null
    }

}