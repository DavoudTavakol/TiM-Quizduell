package de.mmapp.quiz_frontend.models

data class Game(
    var gameId: String,
    val player1: Player,
    var player2: Player,
    var categories: List<String>,
    var questionList: MutableList<Question>,
    var gameStatus: GameStatus,
)