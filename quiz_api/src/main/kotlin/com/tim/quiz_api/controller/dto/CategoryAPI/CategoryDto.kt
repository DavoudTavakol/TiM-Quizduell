package com.tim.quiz_api.controller.dto.CategoryAPI

import com.tim.quiz_api.controller.dto.CategoryAPI.min.CategoryMinDto

data class CategoryDto (val categories: List<CategoryMinDto>, val countCategories:Int)