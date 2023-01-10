package com.tim.quiz_api.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*




/*
https://www.baeldung.com/spring-boot-mongodb-auto-generated-field
Aufgabe Funktion zum Auto-generieren der ID (Robert)
 */

@Document("categories")
class Category(
    var categoryName: String,
    var questions: MutableList<Question>,
    var iconURL:String = "",
    var desc:String = "",
    @Id val id:String = UUID.randomUUID().toString(),
)