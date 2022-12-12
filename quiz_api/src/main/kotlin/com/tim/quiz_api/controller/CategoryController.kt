package com.tim.quiz_api.controller

import com.tim.quiz_api.controller.dto.CategoryAPI.CreateCategoryDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.data.Question
import com.tim.quiz_api.repository.CategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


/*
Aufgabe: Error Handling (Rick)
 */

@RestController
@RequestMapping("/api/category", "application/json")
class CategoryController @Autowired constructor(val categoryRepo: CategoryRepo) {



    /*
        Liefert alle Kategorie und dazugehörige Questions aus der Collection "categories" zurück
     */
    @GetMapping()
    fun getAllCategories(): ResponseEntity<List<Category>> {
        return ResponseEntity.ok(categoryRepo.findAll())
    }

    /*
        Erstellt ein NEUES Dokument in der "category" collection mit dem übergebenen Namen z.B.: {categoryName: "Geschichte"}
        @RequestBody bedeutet wird extrahieren die Daten welche das FE uns im JSON Format ({categoryName: "Geschichte"}) im RequestBody mitgesendet hat
        → Deswegen habe ich diesen requestBody mit einer Klasse modelliert CreateCategoryDto
     */
    @PostMapping()
    fun createCategory(@RequestBody category: CreateCategoryDto): ResponseEntity<Category> {
        val emptyListOfQuestions = listOf<Question>()
        val savedCategory = categoryRepo.save(Category(category.categoryName, emptyListOfQuestions))
        return ResponseEntity.ok(savedCategory)
    }

}