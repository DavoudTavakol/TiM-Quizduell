package com.tim.quiz_api.controller

import com.tim.quiz_api.data.Category
import com.tim.quiz_api.data.Question
import com.tim.quiz_api.repository.CategoryRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/*
Aufgabe: Error Handling (Rick)
 */

@RestController
@RequestMapping("/category")
class CategoryController @Autowired constructor(val categoryRepo: CategoryRepo) {



    /*
        Liefert alle Kategorie und Questions in der Collection categories zurück
     */
    @GetMapping()
    fun getAllCategories(): ResponseEntity<List<Category>> {
        return ResponseEntity.ok(categoryRepo.findAll())
    }

    @GetMapping("/insert")
    fun insert(): ResponseEntity<Category> {
        val testCategoryID = "942f3c05-bba2-47dc-8b37-11dc8847a1b5";
        //fügt man der Category bei einem save eine ID hinzu welche bereits existiert führt save einen update auf die collection aus, kein insert
        return ResponseEntity.ok(
            categoryRepo.save(
                Category(
                    "UUID Test Category",
                    listOf(Question("Question Title", listOf(), testCategoryID)),
                    testCategoryID)))
    }


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