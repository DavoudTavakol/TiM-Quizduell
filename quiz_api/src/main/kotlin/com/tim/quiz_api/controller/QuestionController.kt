package com.tim.quiz_api.controller

import com.tim.quiz_api.data.Question
import com.tim.quiz_api.repository.QuestionRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/*

@RestController ist ähnlich wie @Controller allerdings wir dieser nicht verwendet um eine HTML Ressource auszuliefern
sondern um eine Schnittstelle zu Kennzeichnen
Hier findet sich das API-Team wieder!
 */
@RestController
@RequestMapping("/questions")
class QuestionController @Autowired constructor(val questionRepo: QuestionRepo) {

    @GetMapping()
    fun getAllQuestions(): ResponseEntity<List<Question>> {
        val listQuestions = questionRepo.findAll()
        return ResponseEntity.ok(listQuestions)
    }

    @GetMapping("/1")
    fun getQuestion() = questionRepo.insert(Question(id = "M3", question = "Q!?", answer = "A1", points = 5))
}