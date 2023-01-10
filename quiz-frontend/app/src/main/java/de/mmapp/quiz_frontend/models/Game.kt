package de.mmapp.quiz_frontend.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    var id: String,
    var gameId: String,
    val player1: Player,
    var player2: Player,
    var categories: List<String>,
    var questionList: List<Question>,
    var gameStatus: GameStatus,
) : Parcelable