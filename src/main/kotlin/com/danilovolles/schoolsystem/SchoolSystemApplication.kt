package com.danilovolles.schoolsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SchoolSystemApplication

fun main(args: Array<String>) {
    runApplication<SchoolSystemApplication>(*args)
}
