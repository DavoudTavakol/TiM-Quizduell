package com.tim.quiz_api.data

data class Game(
    var gameId: String,
    val player1: Player,
    var player2: Player,
    var questionList : MutableList<Question>,
    var gameStatus: GameStatus,
    var token : String,
    var answers1 : List<Boolean>,
    var answers2: List<Boolean>
    )