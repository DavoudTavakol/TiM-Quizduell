package com.tim.quiz_api.controller

import com.tim.quiz_api.controller.dto.CreateCategoryDto
import com.tim.quiz_api.data.Category
import com.tim.quiz_api.data.Question
import com.tim.quiz_api.repository.CategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/*
Aufgabe: Error Handling (Rick)
 */

@RestController
@RequestMapping("/category")
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
    fun createCategory(@RequestBody category:CreateCategoryDto): ResponseEntity<Category> {
        val emptyListOfQuestions = listOf<Question>()
        val savedCategory = categoryRepo.save(Category(category.categoryName, emptyListOfQuestions))
        return ResponseEntity.ok(savedCategory)
    }

    /*@GetMapping("/insert")
    fun insert(): ResponseEntity<Category> {
        val testCategoryID = "942f3c05-bba2-47dc-8b37-11dc8847a1b5";
        //fügt man der Category bei einem save eine ID hinzu welche bereits existiert führt save einen update auf die collection aus, kein insert
        return ResponseEntity.ok(
            categoryRepo.save(
                Category(
                    "Fragen zum Thema Programmieren in Kotlin",
                    listOf(Question("Question Title", listOf(), testCategoryID)),
                    testCategoryID)))
    }*/


   /* @GetMapping("/add/{collectionName}")
    //durch POST ersetzen
    fun createCategory(@PathVariable collectionName:String): ResponseEntity<String> {
        val category = db.listCollectionNames().find{ it==collectionName }
         if(category != null){
            return ResponseEntity.badRequest().body("Collection Already Exists")
        }else{
            db.createCollection(collectionName)
            return ResponseEntity.ok("Category created")
        }
    }*/

    /*@GetMapping("/createTestDoc")
    fun createTestDoc(): MutableList<Category> {
        val categories = listOf<Category>(
            Category("Mathematik", "Math", listOf("Q1")),
            Category("Sport", "Sport", listOf("Q2"))
        )
        return categoryRepo.saveAll(categories)
    }*/
}