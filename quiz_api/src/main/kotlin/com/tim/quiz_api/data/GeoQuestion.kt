package com.tim.quiz_api.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(value = "Geography")
data class GeoQuestion (
    @Id
    val id:String,
    val question:String,
    val answer:String,
    val points:Int
)