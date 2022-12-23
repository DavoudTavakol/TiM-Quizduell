package com.tim.quiz_api.controller.dto.CategoryAPI.min

import com.tim.quiz_api.data.Answer

data class QuestionMinDto(val question:String, val answers: List<Answer>)
