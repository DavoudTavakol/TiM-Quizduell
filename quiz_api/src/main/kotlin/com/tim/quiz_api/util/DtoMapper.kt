package com.tim.quiz_api.util

import com.tim.quiz_api.controller.dto.CategoryAPI.CreateCategoryDto
import com.tim.quiz_api.controller.dto.CategoryAPI.QuestionListDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.CategoryMinDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.QuestionMinDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.data.Question

object DtoMapper {

    fun questionsDTOtoQuestions(questions:List<QuestionMinDto>, categoryId:String): MutableList<Question> {
        return questions.map{Question(it.question, it.answers, categoryId)}.toMutableList()
    }

    fun categoriesToCategoryMinDTOs(categories:List<Category>): List<CategoryMinDto> {
        return categories.map { CategoryMinDto(it.id, it.categoryName, it.iconURL, it.desc) }
    }

}