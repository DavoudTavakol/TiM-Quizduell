package com.tim.quiz_api.repository

import com.tim.quiz_api.data.Category
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepo : MongoRepository<Category, String>{
    fun findCategoryByCategoryName(categoryName:String):Category?
    fun existsByCategoryName(categoryName: String):Boolean
}