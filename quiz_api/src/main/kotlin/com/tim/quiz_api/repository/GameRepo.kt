package com.tim.quiz_api.repository

import com.tim.quiz_api.data.Game
import com.tim.quiz_api.data.GameStatus
import com.tim.quiz_api.data.Player
import com.tim.quiz_api.data.Question

class GameRepo() {

    companion object {
        private var instance : GameRepo? = null

         var games : MutableMap<String, Game> = mutableMapOf(
             "Game0" to Game(
                 "game0",
                 player1 = Player(""),
                 player2 = Player(""),
                 listOf<String>(),
                 mutableListOf<Question>(),
                 gameStatus = GameStatus.NEW,
                 answers1 = listOf(),
                 answers2 = listOf(),

             ))


        fun  getInstance(): GameRepo {
            if (instance == null)  // NOT thread safe!
                instance = GameRepo()

            return instance!!
        }

        fun getGame(key : String) : Game? {
            return games.get(key)
        }




    }


}
