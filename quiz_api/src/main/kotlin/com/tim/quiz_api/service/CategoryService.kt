package com.tim.quiz_api.service

import com.tim.quiz_api.controller.dto.CategoryAPI.CategoryDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.repository.CategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryService @Autowired constructor(private val categoryRepo: CategoryRepo) {

    /*
        Returns CategoryDto (CategoryName and ID) without Questions
     */
    fun getAllCategories(): List<CategoryDto> {
        val categoriesAndQuestions = categoryRepo.findAll()
        return categoriesAndQuestions.map { CategoryDto(it.id, it.categoryName) }
    }

    /*
        Returns Category and its questions
     */
    fun getCategoryById(id:String): Category? {
        return categoryRepo.findByIdOrNull(id)
    }
}