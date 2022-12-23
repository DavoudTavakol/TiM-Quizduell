package com.tim.quiz_api.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


data class Score(
    val score : Int,
    val nickname : String,
    val id:String? = UUID.randomUUID().toString()
) {

}