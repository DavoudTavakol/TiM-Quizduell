package com.tim.quiz_api.util

import com.tim.quiz_api.controller.dto.CategoryAPI.QuestionDto
import com.tim.quiz_api.data.Question

object DtoMapper {

    fun questionsDtoToQuestions(questions:List<QuestionDto>, categoryId:String): List<Question> {
        return questions.map{Question(it.question, it.answers, categoryId)}
    }
}