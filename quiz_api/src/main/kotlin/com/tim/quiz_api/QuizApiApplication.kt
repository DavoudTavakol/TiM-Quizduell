package com.tim.quiz_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


/*
Einstiegspunkt Kotlin Spring Doku
https://kotlinlang.org/docs/jvm-create-project-with-spring-boot.html
 */
@SpringBootApplication
class QuizApiApplication


fun main(args: Array<String>) {
    runApplication<QuizApiApplication>(*args)

}
