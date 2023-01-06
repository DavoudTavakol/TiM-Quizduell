package com.tim.quiz_api.service

import com.tim.quiz_api.controller.dto.CategoryAPI.CreateCategoryDto
import com.tim.quiz_api.controller.dto.CategoryAPI.QuestionListDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.CategoryMinDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.QuestionMinDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.repository.CategoryRepo
import com.tim.quiz_api.util.DtoMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryService @Autowired constructor(private val categoryRepo: CategoryRepo) {

    /*
        Returns a List of CategoryMinDTOS (CategoryName and ID) without Questions
     */
    fun getAllCategories(): List<CategoryMinDto> {
        return DtoMapper.categoriesToCategoryMinDTOs(categoryRepo.findAll())
    }

    /*
        Returns Category and its questions
     */
    fun getCategoryById(id: String): Category? {
        return categoryRepo.findByIdOrNull(id);
    }

    fun saveCategory(createCategory: CreateCategoryDto): Category {
        /*
            Create basic category object, at this point ID is already set to a random UUID.
            It is important for the UUID to be set before we save a list of questions,
            because questions depend on the categoryID as a foreign key
         */
        val category = Category(createCategory.categoryName, mutableListOf())
        //set questions of category to mapped questions list, if the provided list is not empty
        category.questions =
            if(createCategory.questions.isNotEmpty())
            DtoMapper.questionsDTOtoQuestions(createCategory.questions, category.id)
            else category.questions
        //save category object to database
        return categoryRepo.save(category)
    }

    /*
        Checks if categoryName is set and then proceeds to save category
        If category Name is blank
     */

    fun isValidCategoryName(categoryName:String):Boolean{
        if(categoryName.isNullOrEmpty() || categoryExists(categoryName)) return false
        return true
    }

    fun categoryExists(categoryName: String): Boolean {
        return categoryRepo.existsByCategoryName(categoryName)
    }
}

