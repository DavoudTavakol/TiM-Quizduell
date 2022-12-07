package com.tim.quiz_api.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tim.quiz_api.data.Game
import com.tim.quiz_api.data.GameStatus
import com.tim.quiz_api.data.Player
import com.tim.quiz_api.data.Question
import com.tim.quiz_api.repository.GameRepo
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import java.util.*

@Service
@AllArgsConstructor
class GameService {

    fun createGame(player : Player): Game {
        var game : Game = Game(
            gameId = UUID.randomUUID().toString().substring(0,5),
            player1 = player,
            player2 = Player(""),
            mutableListOf<Question>(),
            GameStatus.NEW,
            "",
            answers1 = listOf(),
            answers2 = listOf()
            )
        GameRepo.games.put(game.gameId,game)
        game.token = "toki"

        return game

    }

    fun connectToGame(gameId : String, player2 : Player): Game? {

        var game : Game? = GameRepo.getGame(gameId)
        if (game != null) {
            game.player2 = player2
            game.gameStatus = GameStatus.IN_PROGRESS
            game.token = ""
        }

        return game


    }

    fun gameResult(gameId : String , token : String ,answers : List<Boolean > ) : Game? {

        var game : Game? = GameRepo.getGame(gameId)


        if (game != null) {

            if (token != "") {
                game.answers1 = answers
            } else {
                game.answers2 = answers
                game.gameStatus = GameStatus.FINISHED

            }


           // game.token = token
           // game.answers1 = answers

        }
        return  game
    }
}