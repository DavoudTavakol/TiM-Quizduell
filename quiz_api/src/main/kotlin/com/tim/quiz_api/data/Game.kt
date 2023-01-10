package com.tim.quiz_api.data

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("games")
data class Game(
    val id:String? = UUID.randomUUID().toString(),
    var gameId: String,
    val player1: Player,
    var player2: Player,
    var categories: List<String>,
    var questionList: List<Question>,
    var gameStatus: GameStatus,
)