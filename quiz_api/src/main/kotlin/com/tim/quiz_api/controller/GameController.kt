package com.tim.quiz_api.controller

import com.tim.quiz_api.controller.dto.*
import com.tim.quiz_api.controller.dto.CategoryAPI.min.CategoryMinDto
import com.tim.quiz_api.data.*
import com.tim.quiz_api.repository.CategoryRepo
import com.tim.quiz_api.service.CategoryService
import com.tim.quiz_api.service.GameService
import com.tim.quiz_api.service.HighscoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/game")
class GameController @Autowired constructor(
    val categoryRepo: CategoryRepo,
    val highscoreService : HighscoreService,
    private val gameService: GameService,

    ) {

    @PostMapping("/create")
    fun createGame(@RequestBody player: Player ) :
            ResponseEntity<Game> {


        return ResponseEntity.ok(gameService?.createGame(player))
    }

    @PostMapping("/connect")
    fun connectToGame(@RequestBody request : ConnectRequest) :
            ResponseEntity<Game> {
        return ResponseEntity.ok(gameService?.connectToGame(request.gameId, request.player2))
    }

    @PostMapping("/ready")
    fun setReady(@RequestBody request : ReadyRequest) :
            ResponseEntity<List<Player>> {
        return ResponseEntity.ok(gameService?.setReady(request.nickname,request.gameId, request.categories))
    }

    @PostMapping("/check")
    fun checkPlayer(@RequestBody checkRequest: CheckRequest) : ResponseEntity<Boolean> {
        return ResponseEntity.ok(gameService?.isPlayerReady(checkRequest.gameId, checkRequest.nickname))
    }

    @PostMapping("/submitanswers")
    fun submitAnswers(@RequestBody request : SubmitRequest ):
            ResponseEntity<Game> {

        var game = gameService?.submitAnswers(request.gameId, request.nickname, request.answers, request.time)

        if(game != null && game.gameStatus === GameStatus.FINISHED){
            highscoreService.updateHighscore(game.player1.score, game.player1.nickname)
            highscoreService.updateHighscore(game.player2.score, game.player2.nickname)
        }

        return ResponseEntity.ok(game)
    }

    @GetMapping("/categories")
    fun getAllCategories(): ResponseEntity<MutableList<String>> {
        val categories = categoryRepo.findAll() as List<Category>
        val itr = categories.iterator()
        var namesList : MutableList<String> = mutableListOf()

        while (itr.hasNext()){
            namesList.add(itr.next().categoryName)
        }
        return ResponseEntity<MutableList<String>>(namesList, HttpStatus.OK)
    }
}