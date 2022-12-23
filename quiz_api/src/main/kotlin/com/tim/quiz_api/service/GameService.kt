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

    fun submitAnswers(gameId : String , nickname : String ,answers : List<Answer>, time : Float ) : Game? {

        var game : Game? = GameRepo.getGame(gameId)
        if (game != null) {

            var score = getScore(answers, time)

            if (nickname == game.player1.nickname) {
                game.answers1 = answers
                game.score1 = score
                game.time1 = time
            } else if (nickname == game.player2.nickname){
                game.answers2 = answers
                game.score2 = score
                game.time2 = time
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

    fun getScore(answers : List<Answer>, timeNeeded : Float): Int{
        var correctAnswers = getCorrectAnswerCount(answers)
        return calculateScore(correctAnswers.toFloat(), timeNeeded)
    }

    fun getCorrectAnswerCount(answers : List<Answer>): Int{
        var correctAnswers = 0
        for (answer in answers){
            if(answer.isAnswerCorrect) correctAnswers+=1
        }

        return correctAnswers
    }

    fun calculateScore(correctAnswers : Float = 0.0f, timeNeeded : Float = 60.0f): Int{

        if(correctAnswers == 0.0f) return 0

        if(correctAnswers == 10) return ((60 - timeNeeded) * 10 + 200).toInt()

        return ((60 - timeNeeded) / (10 - correctAnswers) * 10 + correctAnswers * 10).toInt()
    }

}