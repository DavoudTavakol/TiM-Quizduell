package com.tim.quiz_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer


/*
Einstiegspunkt Kotlin Spring Doku
https://kotlinlang.org/docs/jvm-create-project-with-spring-boot.html
 */
@SpringBootApplication
class QuizApiApplication : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(QuizApiApplication::class.java)
    }


}


fun main(args: Array<String>) {
    runApplication<QuizApiApplication>(*args)

}
