package com.tim.quiz_api.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document("categories")
class CategoriesAndQuestions(@Id val id:Long) {

}