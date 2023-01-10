package com.tim.quiz_api.service

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
        if (game != null) {
            game.player2 = player2
            game.gameStatus = GameStatus.IN_PROGRESS
            return game
        }

        return null
    }

    fun submitAnswers(gameId : String , nickname : String, answers : List<Answer>, time : Float ) : Game? {

        var game: Game? = GamesLocalRepo.getGame(gameId)
        if (game != null) {

            var score = getScore(answers, time)

            if (nickname == game.player1.nickname) {
                game.player1.answers = answers
                game.player1.score = score
                game.player1.time = time
            } else if (nickname == game.player2.nickname){
                game.player2.answers = answers
                game.player2.score = score
                game.player2.time = time
            }

            if(game.gameStatus == GameStatus.IN_PROGRESS)
                game.gameStatus = GameStatus.HALFFINISHED
            else if(game.gameStatus == GameStatus.HALFFINISHED)
                game.gameStatus = GameStatus.FINISHED

        }
        return game
    }

    fun isPlayerReady(gameId: String, nickname: String): Boolean {
        var game: Game? = GamesLocalRepo.getGame(gameId)

        if(game!!.player1.nickname == nickname){
            return game!!.player2.isReady
        } else if (game!!.player2.nickname == nickname) {
            return game!!.player1.isReady
        } else {
            return false
        }
    }

    fun setReady(nickname : String, gameId: String, categories : List<String>, questionList : List<Question>): Game {
        var game: Game? = GamesLocalRepo.getGame(gameId)

        if (game!!.player1.nickname == nickname){
            game.player1.isReady = true
            game.categories = categories
            game.questionList = questionList
        } else if (game!!.player2.nickname == nickname){
            game.player2.isReady = true
        }

        return game
    }

    fun getScore(answers : List<Answer>, timeNeeded : Float): Int{
        var correctAnswers = getCorrectAnswerCount(answers)
        return calculateScore(correctAnswers, timeNeeded)
    }

    fun getCorrectAnswerCount(answers : List<Answer>): Int{
        var correctAnswers = 0
        for (answer in answers){
            if(answer.isAnswerCorrect) correctAnswers+=1
        }

        return correctAnswers
    }

    fun calculateScore(correctAnswers : Int = 0, timeNeeded : Float = 60.0f): Int{

        if(correctAnswers == 0) return 0

        if(correctAnswers == 10) return ((60 - timeNeeded) * 10 + 200).toInt()

        return ((60 - timeNeeded) / (10 - correctAnswers) * 10 + correctAnswers * 10).toInt()
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