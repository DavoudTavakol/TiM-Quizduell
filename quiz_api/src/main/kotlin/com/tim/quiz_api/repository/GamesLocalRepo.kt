package com.tim.quiz_api.repository

import com.tim.quiz_api.data.Game
import com.tim.quiz_api.data.GameStatus
import com.tim.quiz_api.data.Player
import com.tim.quiz_api.data.Question
import java.util.*

class GamesLocalRepo {

    companion object {
        private var instance : GamesLocalRepo? = null

        var games : MutableMap<String, Game> = mutableMapOf("Game0" to Game(
            gameId = "0000",
            player1 = Player(""),
            player2 = Player(""),
            categories = listOf<String>(),
            questionList = mutableListOf<Question>(),
            gameStatus = GameStatus.NEW,
        ))


        fun  getInstance(): GamesLocalRepo {
            if (instance == null)  // NOT thread safe!
                instance = GamesLocalRepo()

            return instance!!
        }

        fun getGame(key : String) : Game? {
            return games.get(key)
        }


    }

}