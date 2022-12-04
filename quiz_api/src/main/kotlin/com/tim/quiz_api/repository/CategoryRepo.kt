package com.tim.quiz_api.repository

import com.tim.quiz_api.data.Category
import org.springframework.data.mongodb.repository.MongoRepository

interface CategoryRepo : MongoRepository<Category, String>