package de.mmapp.quiz_frontend.models

import de.mmapp.quiz_frontend.models.Answer
import java.util.*


data class Question(
    val question: String,
    val answer: List<Answer>,
    val categoryId: String,
    val id:String? = UUID.randomUUID().toString(),
) {

}