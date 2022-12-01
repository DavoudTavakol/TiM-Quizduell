package com.tim.quiz_api.repository

import com.tim.quiz_api.data.Question
import org.springframework.data.mongodb.repository.MongoRepository

interface QuestionRepo : MongoRepository<Question, String>