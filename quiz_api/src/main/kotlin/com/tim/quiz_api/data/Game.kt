package com.tim.quiz_api.data

data class Game(
    var gameId: String,
    val player1: Player,
    var player2: Player,
    var categories : List<String>,
    var questionList : MutableList<Question>,
    var gameStatus: GameStatus,
    var answers1 : List<Answer>,
    var answers2: List<Answer>
    )