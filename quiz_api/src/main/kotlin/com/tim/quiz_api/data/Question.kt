package com.tim.quiz_api.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


data class Question(
    val question: String,
    val answer: List<Answer>,
    val categoryId: String,
    val id:String = UUID.randomUUID().toString(),
) {

}