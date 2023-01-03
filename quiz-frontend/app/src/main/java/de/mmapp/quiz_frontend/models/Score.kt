package de.mmapp.quiz_frontend.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Score(
    val score : Int,
    val nickname : String,
    val id:String? = UUID.randomUUID().toString()
) : Parcelable {

}