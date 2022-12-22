package com.tim.quiz_api.util

import com.tim.quiz_api.controller.dto.CategoryAPI.QuestionListDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.QuestionMinDto
import com.tim.quiz_api.data.Question

object DtoMapper {

    fun questionsDtoToQuestions(questions:List<QuestionMinDto>, categoryId:String): MutableList<Question> {
        return questions.map{Question(it.question, it.answers, categoryId)}.toMutableList()
    }
}