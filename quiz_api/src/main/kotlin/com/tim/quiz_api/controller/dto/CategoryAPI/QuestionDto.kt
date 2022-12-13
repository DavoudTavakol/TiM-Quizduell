package com.tim.quiz_api.controller.dto.CategoryAPI

import com.tim.quiz_api.data.Answer

data class QuestionDto (val question:String, val answers: List<Answer>) {
}