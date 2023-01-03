package com.tim.quiz_api.controller

import com.tim.quiz_api.data.Score
import com.tim.quiz_api.repository.HighscoreRepo
import com.tim.quiz_api.service.HighscoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/highscore")
class HighscoreController @Autowired constructor(val highscoreRepo : HighscoreRepo, val highscoreService: HighscoreService
){

    @GetMapping("/getTopTen")
    fun getHighscoreList(): ResponseEntity<List<Score>> {
        val highscores = highscoreRepo.findAll(Sort.by(Sort.Direction.DESC, "score")) as List<Score>
        return ResponseEntity<List<Score>>(highscores, HttpStatus.OK)
    }

    /*
    * For debugging purposes
    */
    @GetMapping("/insertHighscore")
    fun insertHighscore(highscore: Score) : List<Score>{
        highscoreService.updateHighscore(highscore.score, highscore.nickname)
        return highscoreService.getHighscoreList()
    }

}