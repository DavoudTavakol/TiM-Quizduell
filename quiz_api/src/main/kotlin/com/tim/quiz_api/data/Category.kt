package com.tim.quiz_api.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Category(
    val name: String,
    @Id val id: String,
    val questionReference: List<String> = mutableListOf()
)