package com.tim.quiz_api.exceptions.exceptionDTO

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class CustomExceptionDTO (val msg:String, val date: LocalDateTime, val statusCode:Int) {
}