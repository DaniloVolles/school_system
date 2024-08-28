package com.danilovolles.schoolsystem.service

import com.danilovolles.schoolsystem.dto.*
import com.danilovolles.schoolsystem.entity.SchoolClass
import com.danilovolles.schoolsystem.repository.SchoolClassRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*
import kotlin.math.E

@Component
class SchoolClassServiceImplementation : SchoolClassService {

    @Autowired
    private lateinit var schoolClassRepository: SchoolClassRepository
    override fun createClass(newClass: SchoolClassInputDTO): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            this.verifyIfSchoolClassExists(newClass)

            val savingClass = SchoolClass(
                id = null,
                name = newClass.name,
                subject = newClass.subject,
                description = newClass.description,
                teacher = newClass.teacher
            )

            schoolClassRepository.save(savingClass)

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, "Class created successfully"))

        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }

    override fun getAllClasses(): ResponseEntity<ApiResponseDTO<Any>> {
        try {

            val classes: List<SchoolClass> = schoolClassRepository.findAll()
            val classesList: MutableList<SchoolClassOutputDTO> = mutableListOf()

            for (schoolClass in classes) {
                val classOutput = SchoolClassOutputDTO(
                    subject = schoolClass.subject,
                    name = schoolClass.name,
                    teacher = schoolClass.teacher,
                    description = schoolClass.description
                )
                classesList.add(classOutput)
            }

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO(ApiResponseStatus.SUCCESS.name, classesList))

        } catch (e: Exception) {
            e.stackTrace
            throw Exception(e.message)
        }
    }

    override fun getClassesByTeacher(teacherId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun getClassesByStudent(studentId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun insertTeacher(teacherId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun insertStudent(studentsIds: Set<UUID>): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    override fun inactiveClass(classId: UUID): ResponseEntity<ApiResponseDTO<Any>> {
        TODO("Not yet implemented")
    }

    private fun verifyIfSchoolClassExists(schoolClass: SchoolClassInputDTO): SchoolClass? {
        val bySubject = schoolClassRepository.getSchoolClassBySubject(schoolClass.subject)
        val byName = schoolClassRepository.getSchoolClassByName(schoolClass.name)
        if (bySubject == null && byName == null) {
            throw Exception("SchoolClass already in our database")
        }
        return null
    }


}