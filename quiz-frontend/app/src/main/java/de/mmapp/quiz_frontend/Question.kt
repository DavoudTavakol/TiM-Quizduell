package de.mmapp.quiz_frontend

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class Question : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions_activity)

        val card = findViewById<CardView>(R.id.card)
        card.setCardBackgroundColor(Color.rgb(240, 240, 240))
        card.cardElevation = 10.0F
        setQuestion()
        setAnswers()
        setRound()


        // start progress bar
        var i = 10

        val myProgressBar: ProgressBar = findViewById<ProgressBar>(R.id.progressbar)
        myProgressBar.progress = i

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


    private fun setQuestion() {
        // ToDo get the question from API
        val question = findViewById<TextView>(R.id.question)
        question.text = "Neue Testfrage"
    }

    private fun setAnswers() {
        // ToDo get answers from API
        val answer1 = findViewById<Button>(R.id.answer1)
        answer1.text = "Antwort1"
        val answer2 = findViewById<Button>(R.id.answer2)
        answer2.text = "Antwort2"
        val answer3 = findViewById<Button>(R.id.answer3)
        answer3.text = "Antwort3"
        val answer4 = findViewById<Button>(R.id.answer4)
        answer4.text = "Antwort4"
    }

    private fun setRound() {
        // ToDo get round
        val round = findViewById<TextView>(R.id.round)
        round.text = "Runde 1"
    }
}