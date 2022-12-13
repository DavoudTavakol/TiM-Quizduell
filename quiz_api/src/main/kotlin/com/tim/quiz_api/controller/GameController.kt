package com.tim.quiz_api.controller

import com.mongodb.client.MongoDatabase
import com.tim.quiz_api.controller.dto.*
import com.tim.quiz_api.data.Game
import com.tim.quiz_api.data.Player
import com.tim.quiz_api.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/game")
class GameController @Autowired constructor(var mongoTemplate: MongoTemplate,private final val gameService: GameService
) {
    var db: MongoDatabase = mongoTemplate.db;


    @PostMapping("/create")
    fun createGame(@RequestBody startRequest: StartRequest ) :
            ResponseEntity<Game> {
        return ResponseEntity.ok(gameService?.createGame(startRequest))
    }

    @PostMapping("/connect")
    fun connectToGame(@RequestBody request : ConnectRequest) :
            ResponseEntity<Game> {
        return ResponseEntity.ok(gameService?.connectToGame(request.gameId, request.player2))
    }

    @PostMapping("/ready")
    fun setReady(@RequestBody request : ReadyRequest) :
            ResponseEntity<List<Player>> {
        return ResponseEntity.ok(gameService?.setReady(request.nickname,request.gameId))
    }

    @PostMapping("/check")
    fun checkPlayer(@RequestBody checkRequest: CheckRequest) : ResponseEntity<Boolean> {
        return ResponseEntity.ok(gameService?.isPlayerReady(checkRequest.gameId, checkRequest.nickname))
    }

    @PostMapping("/submitanswers")
    fun submitAnswers(@RequestBody request : SubmitRequest ):
            ResponseEntity<Game> {
        return ResponseEntity.ok(gameService?.submitAnswers(request.gameId, request.nickname, request.answers))
    }

}