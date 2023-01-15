package com.tim.quiz_api.controller

import com.tim.quiz_api.controller.dto.*
import com.tim.quiz_api.controller.dto.CategoryAPI.min.CategoryIdListDTO
import com.tim.quiz_api.data.*
import com.tim.quiz_api.repository.CategoryRepo
import com.tim.quiz_api.repository.GamesLocalRepo
import com.tim.quiz_api.service.GameService
import com.tim.quiz_api.service.HighscoreService
import com.tim.quiz_api.service.QuestionService
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
    private val questionService: QuestionService,

    ) {

    @PostMapping("/create")
    fun createGame(@RequestBody player: Player ) :
            ResponseEntity<Game> {
        return ResponseEntity.ok(gameService?.createGame(player))
    }

    @PostMapping("/connect")
    fun connectToGame(@RequestBody request : ConnectRequest) :
            ResponseEntity<Game> {
        if (request.player2.nickname == GamesLocalRepo.getGame(request.gameId)!!.player1.nickname){
            return ResponseEntity.ok(Game("","0", Player(""), Player(""), listOf(), listOf(),GameStatus.FINISHED))
        } else {
            return ResponseEntity.ok(gameService?.connectToGame(request.gameId, request.player2))
        }
    }

    @PostMapping("/ready")
    fun setReady(@RequestBody request : ReadyRequest) :
            ResponseEntity<Game> {
        val categories = categoryRepo.findAll() as List<Category>
        var categoryIdList : MutableList<String> = mutableListOf()
        for (cat in categories) {
            categoryIdList.add(cat.id)
        }
        val questions = questionService.getRandomQuestionsLimit(CategoryIdListDTO(categoryIdList),10)
        println(questions)
        return ResponseEntity.ok(gameService?.setReady(request.nickname,request.gameId, request.categories, questions))
    }

    @PostMapping("/check")
    fun checkPlayer(@RequestBody checkRequest: CheckRequest) : ResponseEntity<Boolean> {
        return ResponseEntity.ok(gameService?.isPlayerReady(checkRequest.gameId, checkRequest.nickname))
    }

    @PostMapping("/submitanswers")
    fun submitAnswers(@RequestBody request : SubmitRequest ):
            ResponseEntity<Game> {

        var game = gameService?.submitAnswers(request.gameId, request.nickname, request.answers, request.time)

        if(game!!.player1.nickname == request.nickname) {
            highscoreService.updateHighscore(game.player1.score, game.player1.nickname)
        } else if (game!!.player2.nickname == request.nickname) {
            highscoreService.updateHighscore(game.player2.score, game.player2.nickname)
        }

        if(game.gameStatus == GameStatus.HALFFINISHED || game.gameStatus == GameStatus.FINISHED){
            gameService.saveGameInDatabase(game)
            //gameService.deleteGameFromLocalRepo(game.gameId)
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

    // for debugging
    @GetMapping("/deletegame")
    fun deleteGame(gameId: String) {
        gameService.deleteGameInDatabase(gameId)
    }

    @PostMapping("/getgame")
    fun getHighscoreList(@RequestBody gameId: String): ResponseEntity<Game> {
        val game = GamesLocalRepo.getGame(gameId)
        println(game)
        return ResponseEntity<Game>(game, HttpStatus.OK)
    }
}