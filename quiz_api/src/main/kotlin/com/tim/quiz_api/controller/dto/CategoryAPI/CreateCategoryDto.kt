package com.tim.quiz_api.controller.dto.CategoryAPI

data class CreateCategoryDto(val categoryName:String, val questions:List<QuestionDto> = mutableListOf())