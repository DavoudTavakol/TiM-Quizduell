package com.tim.quiz_api.controller

import com.fasterxml.jackson.databind.util.JSONPObject
import com.tim.quiz_api.repository.QuestionRepo
import lombok.Getter
import org.springframework.beans.factory.annotation.Autowired
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
    fun getAllQuestions() = questionRepo.findAll()
}