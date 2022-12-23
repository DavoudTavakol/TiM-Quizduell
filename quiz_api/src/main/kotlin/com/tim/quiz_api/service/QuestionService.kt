package com.tim.quiz_api.service

import com.tim.quiz_api.controller.dto.CategoryAPI.QuestionListDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.data.Question
import com.tim.quiz_api.repository.CategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class QuestionService @Autowired constructor(val categoryRepo: CategoryRepo, val categoryService: CategoryService) {

    fun getQuestionsByCategories(categoryIds:List<String>): List<Question>{
        return listOf<Question>()
    }

    fun getQuestionsByCategoryId(categoryId:String): QuestionListDto?{
        val category = categoryService.getCategoryById(categoryId)
        if (category != null) {
            val categoryName = category.categoryName
            val questions = category.questions
            val count = questions.count()
            val questionDto = QuestionListDto(categoryName, questions, count)
            return questionDto
        }
        return null
    }

    fun createQuestionInCategory(question: Question): Category {
        val categoryId = question.categoryId
        val category = categoryService.getCategoryById(categoryId)
        category?.questions?.add(question)
        return categoryRepo.save(category!!)
    }

    fun getRandomQuestionsLimit(categoryIds:List<String>, limit:Int): List<Question>{
        return listOf<Question>()
    }

    fun getQuestionsById(questionId:String, categoryId:String){

    }

    fun getAnswerByByQuestion(questionId:String, categoryId:String){

    }

}