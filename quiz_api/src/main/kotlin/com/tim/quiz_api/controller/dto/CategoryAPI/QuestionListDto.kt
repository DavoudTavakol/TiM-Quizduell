package com.tim.quiz_api.controller.dto.CategoryAPI

import com.tim.quiz_api.data.Question

data class QuestionListDto (val categoryName: String, val questions:List<Question>, val countQuestions: Int ) {
}