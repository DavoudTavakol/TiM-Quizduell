package com.tim.quiz_api.controller

import com.tim.quiz_api.controller.dto.CategoryAPI.CategoryDto
import com.tim.quiz_api.controller.dto.CategoryAPI.CreateCategoryDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.data.Question
import com.tim.quiz_api.repository.CategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
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
        val categories = categoryRepo.findAll() as List<Category>
        return ResponseEntity<List<Category>>(categories, HttpStatus.OK)
    }

    /*
        Erstellt ein NEUES Dokument in der "category" collection mit dem übergebenen Namen
        z.B.: {categoryName: "Geschichte"}

        @RequestBody bedeutet wird mappen die Daten, welche der Client uns im JSON Format im RequestBody schickt,
        zu einem CreateCategoryDto
     */
    @PostMapping(consumes= ["application/json"])
    fun createCategory(@RequestBody category: CreateCategoryDto): ResponseEntity<Category> {
        val emptyListOfQuestions = listOf<Question>()
        val categoryName = category.categoryName
        //Check if categoryName is not null or empty string
        if(!categoryName.isNullOrBlank()){
            //TODO also check if category with similar name already exists?
            val savedCategory = categoryRepo.save(Category(categoryName, emptyListOfQuestions))
            //Returns a Status 201 Created
            return ResponseEntity<Category>(savedCategory, HttpStatus.CREATED)
        }
        //TODO find out how to customize error message
        //Returns a Status 400 Bad Request
        return ResponseEntity<Category>(null, HttpStatus.BAD_REQUEST)
    }

    /*
        Updated eine bereits bestehende Category
     */
    @PutMapping(consumes= ["application/json"])
    fun updateCategory(@RequestBody category: CategoryDto): ResponseEntity<Category> {
        val newName = category.categoryName
        val id = category.id
        //request category from DB
        val optionalCategory = categoryRepo.findById(id)
        //Check if categoryName is not null or empty string and check if category is actually in DB
        if(!newName.isNullOrBlank() && optionalCategory.isPresent){
            val category = optionalCategory.get()
            //change category name
            category.categoryName = newName

            //Returns a Status 200 OK
            return ResponseEntity<Category>(categoryRepo.save(category), HttpStatus.OK)
        }
        //TODO find out how to customize error message
        //Returns a Status 400 Bad Request
        return ResponseEntity<Category>(null, HttpStatus.BAD_REQUEST)
    }
}