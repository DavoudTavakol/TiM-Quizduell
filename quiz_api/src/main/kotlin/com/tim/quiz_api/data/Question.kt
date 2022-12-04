package com.tim.quiz_api.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class Question(
    val question: String,
    val answer: String,
    val points: Int,
    @Id val id: String = ""
) {

}