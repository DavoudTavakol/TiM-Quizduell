package de.mmapp.quiz_frontend

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.CalendarContract
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class Question : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions_activity)

        val card = findViewById<CardView>(R.id.card)
        card.setCardBackgroundColor(Color.rgb(240, 240, 240))
        card.cardElevation = 10.0F

        // start
        var i = 10

        val myProgressBar: ProgressBar = findViewById<ProgressBar>(R.id.progressbar)
        myProgressBar.progress = i;

        var myCountDownTimer: CountDownTimer = object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                i--
                myProgressBar.progress = i * (10000 / 1000)
            }

            override fun onFinish() {
                // Zeit abgelaufen
            }
        }.start()

    }
}