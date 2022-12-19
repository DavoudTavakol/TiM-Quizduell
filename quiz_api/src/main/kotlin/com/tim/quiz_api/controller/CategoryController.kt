package com.tim.quiz_api.controller

import com.tim.quiz_api.controller.dto.CategoryAPI.CategoryDto
import com.tim.quiz_api.controller.dto.CategoryAPI.CreateCategoryDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.data.Question
import com.tim.quiz_api.repository.CategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("/api/category")
class CategoryController @Autowired constructor(val categoryRepo: CategoryRepo) {


    /*
        Liefert alle Kategorie ohne Fragen aus der Collection "categories" zurück
     */
    @GetMapping()
    fun getAllCategories(): ResponseEntity<List<CategoryDto>> {
        val categoriesAndQuestions = categoryRepo.findAll()
        //Map to CategoryMinDto
        val categories = categoriesAndQuestions.map { CategoryDto(it.id, it.categoryName) }
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
        val emptyListOfQuestions = listOf<Question>()
        val categoryName = category.categoryName
        println(categoryName)
        //Check if categoryName is not null or empty string
        if(!categoryName.isNullOrBlank()){
            //TODO also check if category with similar name already exists?
            val savedCategory = categoryRepo.save(Category(categoryName, emptyListOfQuestions))
            //Returns a Status 201 Created
            return ResponseEntity(savedCategory, HttpStatus.CREATED)
        }
        //Returns a Status 400 Bad Request
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
        val category = categoryRepo.findByIdOrNull(id)
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
        val category = categoryRepo.findByIdOrNull(id)
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
        val category = categoryRepo.findByIdOrNull(id)
        if(category != null){
            categoryRepo.deleteById(id)
            return  return ResponseEntity(null, HttpStatus.NO_CONTENT)
        }else{
            return ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }
}