package com.tim.quiz_api.service

import com.tim.quiz_api.controller.dto.CategoryAPI.QuestionListDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.ReadQuestionMinDto
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
            return QuestionListDto(categoryName, questions, count)
        }
        return null
    }

    fun createQuestionInCategory(question: Question): Category {
        val categoryId = question.categoryId
        val category = categoryService.getCategoryById(categoryId)
        category?.questions?.add(question)
        return categoryRepo.save(category!!)
    }

    fun deleteQuestionInCategory(question:ReadQuestionMinDto):Category? {
        val categoryId = question.categoryId
        val questionId = question.id
        val category = categoryService.getCategoryById(categoryId)

        if(category != null){
            category.questions = category.questions.filter { it.id != questionId }.toMutableList()
            return categoryRepo.save(category)
        }
        return  null
    }

    fun updateQuestionInCategory(newQuestion:Question):Category? {
        val categoryId = newQuestion.categoryId;
        val questionId = newQuestion.id
        val category = categoryService.getCategoryById(categoryId)
        if(category != null){
            // Iterate over questions
            for(i in category.questions.indices){
                val question = category.questions[i]
                //Update if ids match and exit loop
                if(question.id == questionId){
                    category.questions[i] = newQuestion
                    break;
                }
            }
            return categoryRepo.save(category)
        }
        return null
    }

    fun readQuestion(question:ReadQuestionMinDto): Question? {
        val categoryId = question.categoryId;
        val questionId = question.id
        val category = categoryService.getCategoryById(categoryId)
        if(category != null){
            // Iterate over questions
            for(i in category.questions.indices){
                val q = category.questions[i]
                //Update if ids match and exit loop
                if(q.id == questionId){
                    return category.questions[i]
                }
            }
        }
        return null
    }

    fun getRandomQuestionsLimit(categoryIds:List<String>, limit:Int): List<Question>{
        return listOf<Question>()
    }


    fun getAnswerByByQuestion(questionId:String, categoryId:String){
    }

}