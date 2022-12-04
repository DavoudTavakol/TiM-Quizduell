package com.tim.quiz_api.controller

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoIterable
import com.tim.quiz_api.data.MathQuestion
import com.tim.quiz_api.data.Question
import com.tim.quiz_api.repository.MathQuestionRepo
import com.tim.quiz_api.repository.QuestionRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

/*

@RestController ist ähnlich wie @Controller allerdings wir dieser nicht verwendet um eine HTML Ressource auszuliefern
sondern um eine Schnittstelle zu Kennzeichnen
Hier findet sich das API-Team wieder!
 */
@RestController
@RequestMapping("/questions")
class QuestionController @Autowired constructor(val questionRepo: QuestionRepo) {


    @GetMapping()
    fun getAllQuestions(): MutableList<Question> {
        return questionRepo.findAll()
    }

    @GetMapping("createTestDoc")
    fun createTestDoc(): MutableList<Question> {
        val questions = listOf(
            Question("Test Question", "Test answer", 1, "Math", "Q1"),
            Question("Test Question 2", "Test answer 2", 2, "Sport", "Q2")
        )
        return questionRepo.saveAll(questions)
    }

    /*val mongoDb : MongoClient? = null
    @GetMapping("/allmath")
    fun getAllMathQuestions():
       ResponseEntity<List<MathQuestion>> {
           val mquestions = mathQuestionRepo.findAll()
           return ResponseEntity.ok(mquestions)
       }

    @GetMapping("/math")
    fun getMathQuestion():
            ResponseEntity<Optional<MathQuestion>> {
            val mquestion = mathQuestionRepo.findById("M3")
            return ResponseEntity.ok(mquestion)
        }

    @GetMapping("/insert/math")
    fun insertMathQuestion() :
            ResponseEntity<MathQuestion> {
        val ins = mathQuestionRepo.insert(MathQuestion(id = "M4", question = "Q!?", answer = "A1", points = 5))
        return  ResponseEntity.ok(ins)
    }*/
}