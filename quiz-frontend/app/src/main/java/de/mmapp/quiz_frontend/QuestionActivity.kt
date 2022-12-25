package de.mmapp.quiz_frontend

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import de.mmapp.quiz_frontend.models.Answer
import de.mmapp.quiz_frontend.models.Game
import de.mmapp.quiz_frontend.models.Question


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





        // start progress bar
        var i = 10

        val myProgressBar: ProgressBar = findViewById<ProgressBar>(R.id.progressbar)
        myProgressBar.progress = i

        var myCountDownTimer: CountDownTimer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                i--
                myProgressBar.progress = i * (30000 / 1000)
            }

            override fun onFinish() {

                // TO DO:  Sumbit answers request
                println(answerList)
            }
        }.start()


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