package com.tim.quiz_api.controller


import com.mongodb.client.MongoDatabase
import com.tim.quiz_api.controller.dto.CategoryAPI.QuestionListDto
import com.tim.quiz_api.controller.dto.CategoryAPI.min.ReadQuestionMinDto
import com.tim.quiz_api.data.Question
import com.tim.quiz_api.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/*

@RestController ist ähnlich wie @Controller allerdings wir dieser nicht verwendet um eine HTML Ressource auszuliefern
sondern um eine Schnittstelle zu Kennzeichnen
Hier findet sich das API-Team wieder!
 */
@RestController
@RequestMapping("api/questions")
class QuestionController @Autowired constructor(val questionService: QuestionService,) {

    @GetMapping("{categoryId}")
    fun getQuestionsByCategory(@PathVariable categoryId: String): QuestionListDto? {
        return questionService.getQuestionsByCategoryId(categoryId)
    }

    @PostMapping("/create")
    fun createQuestionInCategory(@RequestBody question: Question): ResponseEntity<Nothing> {
        //ID is generated automatically. Only when id is not in RequestBody.
        questionService.createQuestionInCategory(question)
        return ResponseEntity(null, HttpStatus.CREATED)
    }

    @PutMapping("/update")
    fun updateQuestionInCategory(@RequestBody question:Question):ResponseEntity<Nothing>{
        questionService.updateQuestionInCategory(question)
        return ResponseEntity(null, HttpStatus.CREATED)
    }

    @PostMapping("/read")
    fun getQuestionByIdInCategory(@RequestBody question: ReadQuestionMinDto):ResponseEntity<Question>{
        return ResponseEntity(questionService.readQuestion(question), HttpStatus.OK)
    }
}