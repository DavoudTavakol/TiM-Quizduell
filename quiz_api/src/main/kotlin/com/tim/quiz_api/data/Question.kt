package com.tim.quiz_api.data

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Maths")
data class Question (
    val id:String,
    val question:String,
    val answer:String,
    val points:Int
    )