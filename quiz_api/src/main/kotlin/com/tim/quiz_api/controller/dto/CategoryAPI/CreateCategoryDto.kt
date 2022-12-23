package com.tim.quiz_api.controller.dto.CategoryAPI

import com.tim.quiz_api.controller.dto.CategoryAPI.min.QuestionMinDto

data class CreateCategoryDto(val categoryName:String, val questions:List<QuestionMinDto> = mutableListOf())