package com.tim.quiz_api.service

import com.tim.quiz_api.controller.dto.CategoryAPI.QuestionDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.CategoryMinDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.repository.CategoryRepo
import com.tim.quiz_api.util.DtoMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryService @Autowired constructor(private val categoryRepo: CategoryRepo) {

    /*
        Returns CategoryDto (CategoryName and ID) without Questions
     */
    fun getAllCategories(): List<CategoryMinDto> {
        val categoriesAndQuestions = categoryRepo.findAll()
        return categoriesAndQuestions.map { CategoryMinDto(it.id, it.categoryName) }
    }

    /*
        Returns Category and its questions
     */
    fun getCategoryById(id: String): Category? {
        return categoryRepo.findByIdOrNull(id)
    }

    fun createCategory(categoryName: String, questions: List<QuestionDto>): Category? {
        if (categoryName.isNotEmpty() && categoryName.isNotBlank()) {
            var category = categoryRepo.save(Category(categoryName, listOf()))
            return if (questions.isNotEmpty()) {
                category.questions = DtoMapper.questionsDtoToQuestions(questions, category.id)
                categoryRepo.save(category)
            } else {
                category
            }
        }
        return null
    }
}