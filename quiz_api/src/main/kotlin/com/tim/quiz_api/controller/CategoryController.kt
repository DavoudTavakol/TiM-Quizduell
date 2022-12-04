package com.tim.quiz_api.controller

import com.tim.quiz_api.data.Category
import com.tim.quiz_api.repository.CategoryRepo
import com.tim.quiz_api.repository.MathQuestionRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/category")
class CategoryController @Autowired constructor(val categoryRepo: CategoryRepo) {

    @GetMapping()
    fun getAllCategories(): MutableList<Category> {
        return categoryRepo.findAll()
    }

    @GetMapping("/createTestDoc")
    fun createTestDoc(): MutableList<Category> {
        val categories = listOf<Category>(
            Category("Mathematik", "Math", listOf("Q1")),
            Category("Sport", "Sport", listOf("Q2"))
        )
        return categoryRepo.saveAll(categories)
    }
}