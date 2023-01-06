package com.tim.quiz_api.service

import com.tim.quiz_api.controller.dto.CategoryAPI.CreateCategoryDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.CategoryMinDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.repository.CategoryRepo
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.repository.findByIdOrNull

@DataMongoTest
class CategoryServiceTest @Autowired constructor(private val repo:CategoryRepo){

    private val service = CategoryService(repo)
    private val testCategoryName = "TEST_CATEGORY_1"
    @BeforeEach
    fun setUp() {
    }

    @Test
    fun `should provide a list of all categories`() {
        val categories = service.getAllCategories()
        assertTrue(categories.size >= 0)
    }

    @Test
    fun `should give information whether the category name already exists in DB`() {
        val existsA = service.categoryExists(testCategoryName)
        assertTrue(existsA.not())
    }

    @Test
    fun `should give information whether the category name is valid`(){
        val isValid = service.isValidCategoryName(testCategoryName)
        assertTrue(isValid)
    }


    @Test
    fun `should create a category with name TEST_CATEGORY_1 and delete it after`(){
        val category = service.saveCategory(CreateCategoryDto(testCategoryName, mutableListOf()))
        //ID property exists
        assertFalse(category.id.isNullOrEmpty())
        //Category name is equal to testCategoryName
        assertTrue(category.categoryName == testCategoryName)

        //delete created category
        repo.deleteById(category.id)
        val exists = service.categoryExists(testCategoryName)
        assertTrue(exists.not())
    }

    @Test
    fun `should return a category by its ID`() {
        //
    }
}