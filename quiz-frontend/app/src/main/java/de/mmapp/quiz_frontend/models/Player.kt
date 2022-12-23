package de.mmapp.quiz_frontend.models

import de.mmapp.quiz_frontend.models.Answer

data class Player(var nickname : String){

    var isReady : Boolean = false
    var answers: List<Answer> = listOf<Answer>()
    var score: Int = 0
    var time: Float = 0f
}