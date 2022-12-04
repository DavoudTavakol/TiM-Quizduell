package com.tim.quiz_api.controller

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


    @GetMapping("/all")
    fun getAllCategories(): ResponseEntity<List<String>> {
        return ResponseEntity.ok(mongoTemplate.collectionNames.toList())
    }


    @GetMapping("/add/{collectionName}")
    fun createCollection(@PathVariable collectionName:String): ResponseEntity.BodyBuilder {
        mongoTemplate.createCollection(collectionName)
        return ResponseEntity.ok()
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