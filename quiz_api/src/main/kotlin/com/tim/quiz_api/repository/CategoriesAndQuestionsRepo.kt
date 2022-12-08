package com.tim.quiz_api.repository

import com.tim.quiz_api.data.CategoriesAndQuestions
import org.springframework.data.mongodb.repository.MongoRepository

interface CategoriesAndQuestionsRepo : MongoRepository<CategoriesAndQuestions, Long>