package com.tim.quiz_api.service

import com.tim.quiz_api.controller.dto.CheckResponse
import com.tim.quiz_api.data.*
import com.tim.quiz_api.repository.GamesLocalRepo
import com.tim.quiz_api.repository.GamesMongoRepo
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired

@Service
@AllArgsConstructor
class GameService @Autowired constructor(private val gamesMongoRepo: GamesMongoRepo){

    fun createGame(player: Player): Game {

        var game : Game = Game(
            gameId = RandomStringUtils.randomNumeric(6).toString(),
            player1 = player,
            player2 = Player(""),
            categories = listOf<String>(),
            questionList =mutableListOf<Question>(),
            gameStatus= GameStatus.NEW,
            )
        GamesLocalRepo.games[game.gameId] = game
        return game

    }

    fun connectToGame(gameId : String, player2 : Player): Game? {

        var game: Game? = GamesLocalRepo.getGame(gameId)
            game!!.player2 = player2
            game.gameStatus = GameStatus.IN_PROGRESS

        return game
    }

    fun submitAnswers(gameId : String , nickname : String, answers : List<Answer>, time : Float ) : Game? {

        var game: Game? = GamesLocalRepo.getGame(gameId)
        if (game != null) {

            var score = getScore(answers, time)

            println(game.player1.nickname)
            println(nickname)
            println(nickname == game.player1.nickname)

            if (nickname == game.player1.nickname) {
                println(game.player1.answers)
                game.player1.answers = answers
                game.player1.score = score
                game.player1.time = time
            } else if (nickname == game.player2.nickname){
                println(game.player2.answers)

                game.player2.answers = answers
                game.player2.score = score
                game.player2.time = time
            }

            if(game.player1.answers.isNotEmpty() xor game.player2.answers.isNotEmpty())
                game.gameStatus = GameStatus.HALFFINISHED
            else if(game.player1.answers.isNotEmpty() and game.player2.answers.isNotEmpty())
                game.gameStatus = GameStatus.FINISHED

        }

        GamesLocalRepo.games[game!!.gameId] = game
        return game
    }

    fun isPlayerReady(gameId: String, nickname: String, questions : List<Question>): CheckResponse {
        var game: Game? = GamesLocalRepo.getGame(gameId)
        var response : CheckResponse = CheckResponse(false, listOf())

        if(game!!.player1.nickname == nickname){
            if (game!!.player2.isReady){
                game.questionList = questions
                response.isReady = true
                response.questions = questions
            }
        } else if (game!!.player2.nickname == nickname) {
            if (game!!.player1.isReady){
                game.questionList = questions
                response.isReady = true
                response.questions = questions
            }
        }
        return response
    }

    fun setReady(nickname : String, gameId: String, categories : List<String>): Game {
        var game: Game? = GamesLocalRepo.getGame(gameId)

        if (game!!.player1.nickname == nickname){
            game.player1.isReady = true
            game.categories = categories
        } else if (game!!.player2.nickname == nickname){
            game.player2.isReady = true
        }

        return game
    }

    fun getScore(answers : List<Answer>, timeRemaining : Float): Int{
        var correctAnswers = getCorrectAnswerCount(answers)
        return calculateScore(correctAnswers, timeRemaining)
    }

    fun getCorrectAnswerCount(answers : List<Answer>): Int{
        var correctAnswers = 0
        for (answer in answers){
            if(answer.isAnswerCorrect) correctAnswers+=1
        }

        return correctAnswers
    }

    fun calculateScore(correctAnswers : Int = 0, timeRemaining : Float = 60.0f): Int{

        if(correctAnswers == 0) return 0

        if(correctAnswers == 10) return (timeRemaining * 10 + 200).toInt()

        return (timeRemaining / (10 - correctAnswers) * 10 + correctAnswers * 10).toInt()
    }

    fun saveGameInDatabase(game : Game){
        gamesMongoRepo.save(game)
    }

    fun deleteGameInDatabase(gameId: String){
        gamesMongoRepo.removeGameByGameId(gameId)
    }

    fun deleteGameFromLocalRepo(gameId: String){
        GamesLocalRepo.games.remove(gameId)
    }

}