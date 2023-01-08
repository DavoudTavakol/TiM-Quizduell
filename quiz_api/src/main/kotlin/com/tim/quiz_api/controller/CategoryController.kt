package com.tim.quiz_api.controller

import com.tim.quiz_api.controller.dto.CategoryAPI.CategoryDto
import com.tim.quiz_api.controller.dto.CategoryAPI.CreateCategoryDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.CategoryMinDto
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
@CrossOrigin(origins = ["http://localhost:8080"])
class CategoryController @Autowired constructor(
    val categoryRepo: CategoryRepo,
    val categoryService: CategoryService) {

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

    /**
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
        val(id,categoryName) = category
        val category = categoryService.getCategoryById(id) ?: throw CategoryException("No category found!")
        if(!categoryService.isValidCategoryName(categoryName)) throw CategoryException("Invalid name.")
        category.categoryName = categoryName
        return ResponseEntity(categoryRepo.save(category), HttpStatus.OK)
    }


    /*
        Liefert eine Kategorie und deren Fragen zurück
     */

    @GetMapping("{id}")
    fun findCategoryById(@PathVariable id:String): ResponseEntity<Category> {
        val category = categoryService.getCategoryById(id) ?: throw CategoryException("No category found!")
        return ResponseEntity(category, HttpStatus.OK)
    }


    /*
        Löscht eine Kategorie und alle Fragen zur Kategorie
     */
    @DeleteMapping("{id}")
    fun deleteCategory(@PathVariable id:String):ResponseEntity<Category>{
        categoryService.getCategoryById(id) ?: throw CategoryException("No category found!")
        categoryRepo.deleteById(id)
        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }

    @ExceptionHandler(CategoryException::class)
    fun handleInvalidCategoryNameException(exception: CategoryException): ResponseEntity<CustomExceptionDTO> {
        val exception = CustomExceptionDTO(exception.msg, LocalDateTime.now(), HttpStatus.BAD_REQUEST.value())
        return ResponseEntity(exception, HttpStatus.BAD_REQUEST)
    }
}