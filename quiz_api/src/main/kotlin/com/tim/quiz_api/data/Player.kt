package com.tim.quiz_api.data

data class Player(var nickname : String){

    var isReady : Boolean = false
    var answers: List<Answer> = listOf<Answer>()
    var score: Int = 0
    var time: Float = 0f
}