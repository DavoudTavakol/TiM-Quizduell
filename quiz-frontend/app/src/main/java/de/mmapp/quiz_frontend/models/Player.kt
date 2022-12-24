package de.mmapp.quiz_frontend.models

import android.os.Parcelable
import de.mmapp.quiz_frontend.models.Answer
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(var nickname : String) : Parcelable {

    var isReady : Boolean = false
    var answers: List<Answer> = listOf<Answer>()
    var score: Int = 0
    var time: Float = 0f
}