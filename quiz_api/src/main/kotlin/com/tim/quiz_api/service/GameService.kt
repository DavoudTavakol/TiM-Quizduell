package com.tim.quiz_api.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tim.quiz_api.controller.dto.StartRequest
import com.tim.quiz_api.data.*
import com.tim.quiz_api.repository.GameRepo
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import java.util.*

@Service
@AllArgsConstructor
class GameService {

    fun createGame(startRequest: StartRequest): Game {
        var game : Game = Game(
            gameId = UUID.randomUUID().toString().substring(0,5),
            player1 = startRequest.player1,
            player2 = Player(""),
            startRequest.categories,
            mutableListOf<Question>(),
            GameStatus.NEW,
            answers1 = listOf(),
            answers2 = listOf()
            )
        GameRepo.games.put(game.gameId,game)

        return game

    }

    fun connectToGame(gameId : String, player2 : Player): Game? {

        var game : Game? = GameRepo.getGame(gameId)
        if (game != null) {
            game.player2 = player2
            game.gameStatus = GameStatus.IN_PROGRESS
        }

        return game


    }

    fun submitAnswers(gameId : String , nickname : String ,answers : List<Answer> ) : Game? {

        var game : Game? = GameRepo.getGame(gameId)
        if (game != null) {

            if (nickname == game.player1.nickname) {
                game.answers1 = answers
            } else if (nickname == game.player2.nickname){
                game.answers2 = answers
                game.gameStatus = GameStatus.FINISHED

            }
        }
        return  game
    }

    fun isPlayerReady(gameId: String, nickname: String): Boolean {
        var game : Game? = GameRepo.getGame(gameId)

        if(game!!.player1.nickname == nickname){
            return game!!.player2.isReady
        } else if (game!!.player2.nickname == nickname) {
            return game!!.player1.isReady
        } else {
            return false
        }
    }

    fun setReady(nickname : String, gameId: String): List<Player> {
        var game : Game? = GameRepo.getGame(gameId)

        if (game!!.player1.nickname == nickname){
            game.player1.isReady = true
        } else if (game!!.player2.nickname == nickname){
            game.player2.isReady = true
        }

        return listOf(game.player1, game.player2)
    }

}