package com.tim.quiz_api.controller

import com.tim.quiz_api.controller.dto.CategoryAPI.CategoryDto
import com.tim.quiz_api.controller.dto.CategoryAPI.CreateCategoryDto
import com.tim.quiz_api.controller.dto.CategoryAPI.QuestionDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.data.Question
import com.tim.quiz_api.repository.CategoryRepo
import com.tim.quiz_api.service.CategoryService
import com.tim.quiz_api.util.DtoMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("/api/category")
class CategoryController @Autowired constructor(val categoryRepo: CategoryRepo, val categoryService: CategoryService) {


    /*
        Liefert alle Kategorie ohne Fragen aus der Collection "categories" zurück
     */
    @GetMapping()
    fun getAllCategories(): ResponseEntity<List<CategoryDto>> {
        val categories = categoryService.getAllCategories()
        return ResponseEntity(categories, HttpStatus.OK)
    }

    /*
        Erstellt ein NEUES Dokument in der "category" collection mit dem übergebenen Namen
        z.B.: {categoryName: "Geschichte"}

        @RequestBody bedeutet wird mappen die Daten, welche der Client uns im JSON Format im RequestBody schickt,
        zu einem CreateCategoryDto
     */
    @PostMapping("/create")
    fun createCategory(@RequestBody category: CreateCategoryDto): ResponseEntity<Category> {
        val categoryName = category.categoryName
        val questions:List<QuestionDto> = category.questions
        val savedCategory = categoryService.createCategory(categoryName, questions)
        if(savedCategory != null){
            return ResponseEntity(savedCategory, HttpStatus.CREATED)
        }
        return ResponseEntity(null, HttpStatus.BAD_REQUEST)
    }

    /*
        Updated eine bereits bestehende Category
     */
    @PutMapping("/update")
    fun updateCategory(@RequestBody category: CategoryDto): ResponseEntity<Category> {
        val newName = category.categoryName
        val id = category.id
        //request category from DB
        val category = categoryService.getCategoryById(id)
        //Check if categoryName is not null or empty string and check if category is actually in DB
        if(category != null){
            //change category name
            category.categoryName = newName
            //Returns a Status 200 OK
            return ResponseEntity(categoryRepo.save(category), HttpStatus.OK)
        }
        //Returns a Status 400 Bad Request
        return ResponseEntity(null, HttpStatus.BAD_REQUEST)
    }


    /*
        Liefert eine Kategorie und deren Fragen zurück
     */

    @GetMapping("{id}")
    fun findCategoryById(@PathVariable id:String): ResponseEntity<Category> {
        val category = categoryService.getCategoryById(id)
        return if(category != null){
            ResponseEntity(category, HttpStatus.OK)
        }else{
            ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }


    /*
        Löscht eine Kategorie und alle Fragen zur Kategorie
     */
    @DeleteMapping("{id}")
    fun deleteCategory(@PathVariable id:String):ResponseEntity<Category>{
        val category = categoryService.getCategoryById(id)
        if(category != null){
            categoryRepo.deleteById(id)
            return  return ResponseEntity(null, HttpStatus.NO_CONTENT)
        }else{
            return ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }
}