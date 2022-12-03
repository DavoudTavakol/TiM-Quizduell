package com.tim.quiz_api.repository

import com.tim.quiz_api.data.GeoQuestion
import com.tim.quiz_api.data.HisQuestion
import com.tim.quiz_api.data.MathQuestion
import org.springframework.data.mongodb.repository.MongoRepository


interface HisQuestionRepo : MongoRepository<HisQuestion, String>