package com.tim.quiz_api.controller

import com.tim.quiz_api.controller.dto.CategoryAPI.CategoryDto
import com.tim.quiz_api.controller.dto.CategoryAPI.CreateCategoryDto
import com.tim.quiz_api.controller.dto.CategoryAPI.QuestionListDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.CategoryMinDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.QuestionMinDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.exceptions.CategoryException
import com.tim.quiz_api.exceptions.exceptionDTO.CustomExceptionDTO
import com.tim.quiz_api.repository.CategoryRepo
import com.tim.quiz_api.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/api/category")
class CategoryController @Autowired constructor(
    val categoryRepo: CategoryRepo,
    val categoryService: CategoryService) {


    @GetMapping("/test")
    fun test(): ResponseEntity<Category?> {
        return ResponseEntity(null, HttpStatus.OK)
    }

    /*
        Liefert alle Kategorie ohne Fragen aus der Collection "categories" zurück
     */
    @GetMapping()
    fun getAllCategories(): ResponseEntity<CategoryDto> {
        val categoriesMinDto = categoryService.getAllCategories()
        val numberOfCategories = categoriesMinDto.count()
        val categories = CategoryDto(categoriesMinDto, numberOfCategories)
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
        if(categoryService.isValidCategoryName(category.categoryName)){
            val savedCategory = categoryService.saveCategory(category)
            return ResponseEntity(savedCategory, HttpStatus.CREATED)
        }
        throw CategoryException("Invalid Category Name")
    }

    /*
        Updated eine bereits bestehende Category
     */
    @PutMapping("/update")
    fun updateCategory(@RequestBody category: CategoryMinDto): ResponseEntity<Category> {
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

    @ExceptionHandler(CategoryException::class)
    fun handleInvalidCategoryNameException(exception: CategoryException): ResponseEntity<CustomExceptionDTO> {
        val exception = CustomExceptionDTO(exception.msg, LocalDateTime.now(), HttpStatus.BAD_REQUEST.value())
        return ResponseEntity(exception, HttpStatus.BAD_REQUEST)
    }
}