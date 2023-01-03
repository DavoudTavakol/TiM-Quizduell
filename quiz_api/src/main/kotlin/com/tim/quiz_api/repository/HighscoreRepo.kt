package com.tim.quiz_api.repository

import com.tim.quiz_api.data.Score
import org.springframework.data.mongodb.repository.MongoRepository

interface HighscoreRepo : MongoRepository<Score, String>