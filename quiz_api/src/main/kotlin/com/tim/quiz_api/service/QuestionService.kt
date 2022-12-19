package com.tim.quiz_api.service

import com.tim.quiz_api.data.Question
import com.tim.quiz_api.repository.CategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuestionService @Autowired constructor(val categoryRepo: CategoryRepo) {

    fun getQuestionsByCategories(categoryIds:List<String>): List<Question>{
        return listOf<Question>()
    }

    fun getRandomQuestionsLimit(categoryIds:List<String>, limit:Int): List<Question>{
        return listOf<Question>()
    }

    fun getQuestionsById(questionId:String, categoryId:String){

    }

    fun getAnswerByByQuestion(questionId:String, categoryId:String){

    }
}