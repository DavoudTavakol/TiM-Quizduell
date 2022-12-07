package com.tim.quiz_api.controller

import com.mongodb.client.MongoDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.getCollectionName
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/category")
class CategoryController @Autowired constructor(val mongoTemplate: MongoTemplate) {


    var db: MongoDatabase = mongoTemplate.db;

    @GetMapping("/all")
    fun getAllCategories(): ResponseEntity<List<String>> {
        return ResponseEntity.ok(mongoTemplate.collectionNames.toList())
    }


    @GetMapping("/add/{collectionName}")
    //durch POST ersetzen
    fun createCategory(@PathVariable collectionName:String): ResponseEntity<String> {
        val category = db.listCollectionNames().find{ it==collectionName }
         if(category != null){
            return ResponseEntity.badRequest().body("Collection Already Exists")
        }else{
            db.createCollection(collectionName)
            return ResponseEntity.ok("Category created")
        }

    }

    /*@GetMapping("/createTestDoc")
    fun createTestDoc(): MutableList<Category> {
        val categories = listOf<Category>(
            Category("Mathematik", "Math", listOf("Q1")),
            Category("Sport", "Sport", listOf("Q2"))
        )
        return categoryRepo.saveAll(categories)
    }*/
}