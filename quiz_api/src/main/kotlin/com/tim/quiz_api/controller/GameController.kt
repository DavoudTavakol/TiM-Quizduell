package com.tim.quiz_api.controller

import com.mongodb.client.MongoDatabase
import com.tim.quiz_api.controller.dto.ConnectRequest
import com.tim.quiz_api.controller.dto.SubmitRequest
import com.tim.quiz_api.data.Game
import com.tim.quiz_api.data.Player
import com.tim.quiz_api.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.ResponseEntity
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
    fun createGame(@RequestBody player1 : Player) :
            ResponseEntity<Game> {
        return ResponseEntity.ok(gameService?.createGame(player1))
    }

    @PostMapping("/connect")
    fun connectToGame(@RequestBody request : ConnectRequest) :
            ResponseEntity<Game> {
        return ResponseEntity.ok(gameService?.connectToGame(request.gameId, request.player2))
    }

    @PostMapping("/submitanswers")
    fun submitAnswers(@RequestBody request : SubmitRequest ):
            ResponseEntity<Game> {
        return ResponseEntity.ok(gameService?.gameResult(request.gameId, request.token, request.answers))
    }

}