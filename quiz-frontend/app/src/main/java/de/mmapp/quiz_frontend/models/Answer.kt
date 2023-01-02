package de.mmapp.quiz_frontend.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(
    val answer:String,
    val isAnswerCorrect:Boolean
) : Parcelable