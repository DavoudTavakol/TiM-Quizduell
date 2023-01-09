package com.tim.quiz_api.controller.dto.CategoryAPI.min

data class CategoryListMinDto(val categories:List<CategoryMinDto>, val countCategories:Int, val countQuestions:Int) {}