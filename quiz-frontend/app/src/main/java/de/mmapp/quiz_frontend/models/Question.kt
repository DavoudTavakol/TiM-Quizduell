package de.mmapp.quiz_frontend.models

import android.os.Parcelable
import de.mmapp.quiz_frontend.models.Answer
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Question(
    val question: String,
    val answer: List<Answer>,
    val categoryId: String,
    val id:String? = UUID.randomUUID().toString(),
) : Parcelable {

}