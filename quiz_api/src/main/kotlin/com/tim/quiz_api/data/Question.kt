package com.tim.quiz_api.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Question(
    val question: String,
    val answer: String,
    val points: Int,
    val categoryId:String,
    @Id val id: String = ""
) {

}