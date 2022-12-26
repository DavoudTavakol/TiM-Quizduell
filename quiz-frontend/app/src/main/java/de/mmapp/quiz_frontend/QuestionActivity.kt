package de.mmapp.quiz_frontend

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import de.mmapp.quiz_frontend.models.Answer
import de.mmapp.quiz_frontend.models.Game
import de.mmapp.quiz_frontend.models.Question
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import okio.ByteString.Companion.encodeUtf8


class QuestionActivity : AppCompatActivity() {

    private var questionCount : Int = 0
    private var answerList : MutableList<Answer> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions_activity)
        val card = findViewById<CardView>(R.id.card)
        card.setCardBackgroundColor(Color.rgb(240, 240, 240))
        card.cardElevation = 10.0F

    }

    override fun onResume() {
        super.onResume()


        val game = intent.getParcelableExtra<Game>("game")
        println(game!!.questionList)


        // Init Question and answers
        setQuestionNumber()
        setQuestion(game.questionList.get(0).question)
        setAnswers(game.questionList)



       // 863116

        // start progress bar

        var i : Int = 59
        val myProgressBar: MaterialProgressBar = findViewById<MaterialProgressBar>(R.id.progressbar)
        val timeView = findViewById<TextView>(R.id.gameTimer)
        myProgressBar.progress = 99

       // val colList = ColorStateList.valueOf(170)

       // myProgressBar.progressTintList = colList



        var isFirst : Boolean = true
        myProgressBar.setOnClickListener {

            if (isFirst){
                var myCountDownTimer: CountDownTimer =  object : CountDownTimer(10000, 1000) {

                    override fun onTick(millisUntilFinished: Long) {
                    }

                    override fun onFinish() {
                        var x = myProgressBar.progress
                        timeView.text = x.toString()
                        myProgressBar.progress = 100

                    }
                }.start()

                isFirst = false;
            } else {
                println("clicked")
                myProgressBar.progress += 1

            }


        }


        422195


        var myCountDownTimer: CountDownTimer = object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                i--
                if(myProgressBar.progress > 4 && i >= 10){
                    myProgressBar.progress -= 2
                    timeView.text = ":$i"

                } else if (i in 0..10){
                    timeView.text = "$i"
                    timeView.setTextColor(Color.RED)
                    myProgressBar.supportProgressTintList = ColorStateList.valueOf(Color.RED)
                } else {
                    timeView.text = ""
                    myProgressBar.progress -= 2
                    myProgressBar.progress -= 2


                }
                //myProgressBar.progress = i * (30000 / 1000)
                //println("this is a tick ${myProgressBar.progress}")

            }

            override fun onFinish() {

                // TO DO:  Sumbit answers request
                println(answerList)
            }
        }.start()
        myCountDownTimer


    }

    private fun setQuestion(question : String) {
        // ToDo get the question from API
        val questionView = findViewById<TextView>(R.id.question)
        questionView.text = question
    }

    private fun setAnswers( questionList:  List<Question>) {
        // ToDo get answers from API
        val answer1 = findViewById<Button>(R.id.answer1)
        answer1.text = questionList[questionCount].answer[0].answer

        val answer2 = findViewById<Button>(R.id.answer2)
        answer2.text = questionList[questionCount].answer[1].answer

        // Every Button updates the question and all answers with the next question
        // To do : Save answers to send them in the timer's onFinish() callback

        answer1.setOnClickListener(){

            // Save Answer
            answerList.add( questionList[questionCount].answer[0])

            // Update UI
            questionCount++
            setQuestionNumber()

            setQuestion(questionList[questionCount].question)
            answer1.text = questionList[questionCount].answer[0].answer
            answer2.text = questionList[questionCount].answer[1].answer


        }

        answer2.setOnClickListener(){

            answerList.add( questionList[questionCount].answer[1])

            questionCount++
            setQuestionNumber()

            setQuestion(questionList[questionCount].question)
            answer1.text = questionList[questionCount].answer[0].answer
            answer2.text = questionList[questionCount].answer[1].answer

        }

        val answer3 = findViewById<Button>(R.id.answer3)
        //answer3.text = questionList[0].answer[2].answer
        val answer4 = findViewById<Button>(R.id.answer4)
        //answer4.text = questionList[0].answer[3].answer
    }

    private fun setQuestionNumber() {
        val numberView = findViewById<TextView>(R.id.number)
        var questionNumber : Int = questionCount + 1
        numberView.text = "Frage Nummer: $questionNumber "
    }
}