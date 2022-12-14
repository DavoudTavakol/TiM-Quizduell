package de.mmapp.quiz_frontend

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import de.mmapp.quiz_frontend.models.Answer
import de.mmapp.quiz_frontend.models.Game
import de.mmapp.quiz_frontend.models.Question
import kotlinx.coroutines.runBlocking
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

class QuestionActivity : AppCompatActivity() {

    private var questionCount: Int = 0
    private var answerList: MutableList<Answer> = mutableListOf()
    private var numberOfRightQuestions: Int = 0
    private var nickname: String = ""

    // start of timer
    private var timeLeftInSeconds: Int = 60

    override fun onBackPressed() {
        println("Huhu")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions_activity)
        nickname = intent.getStringExtra("nickname").toString()
        val card = findViewById<CardView>(R.id.card)
        card.setCardBackgroundColor(Color.rgb(240, 240, 240))
        card.cardElevation = 10.0F

    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val game = intent.getParcelableExtra<Game>("game")
        println(game!!.questionList)

        // Init Question and answers
        setQuestionNumber()
        setQuestion(game.questionList[0].question)
        setAnswers(game.questionList)


        val myProgressBar: MaterialProgressBar = findViewById<MaterialProgressBar>(R.id.progressbar)
        val timeView = findViewById<TextView>(R.id.gameTimer)
        val mCountDownTimer: CountDownTimer
        myProgressBar.progress = 100

        mCountDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                println("this is a tick ${myProgressBar.progress}")
                timeLeftInSeconds--
                timeView.text = timeLeftInSeconds.toString()
                myProgressBar.progress = (timeLeftInSeconds * (5.0 / 3.0)).toInt()
            }

            override fun onFinish() {
                // ToDo submit all answers -> API
                submitRequest()
            }
        }

        runBlocking {
            mCountDownTimer.start()
        }

    }


    private fun setQuestion(question: String) {
        val questionView = findViewById<TextView>(R.id.question)
        questionView.text = question
    }

    private fun setAnswers(questionList: List<Question>) {

        val answer1 = findViewById<Button>(R.id.answer1)
        answer1.text = questionList[questionCount].answer[0].answer

        val answer2 = findViewById<Button>(R.id.answer2)
        answer2.text = questionList[questionCount].answer[1].answer

        val answer3 = findViewById<Button>(R.id.answer3)
        answer3.text = questionList[questionCount].answer[2].answer

        val answer4 = findViewById<Button>(R.id.answer4)
        answer4.text = questionList[questionCount].answer[3].answer

        // ToDo : Save answers to send them in the timer's onFinish() callback
        answer1.setOnClickListener() {
            if (questionCount < 9) {
                // Save Answer
                answerList.add(questionList[questionCount].answer[0])

                // Update the points of the player
                updatePoints(0, questionList)

                // Update UI
                questionCount++
                setQuestionNumber()

                // Load next question
                setQuestion(questionList[questionCount].question)

                // Load next answers
                setAnswers(questionList)
            } else {
                submitRequest()
                loadLastActivity()
            }
        }

        answer2.setOnClickListener() {
            if (questionCount < 9) {
                // Save answer
                answerList.add(questionList[questionCount].answer[1])

                // Update the points of the player
                updatePoints(1, questionList)

                questionCount++
                setQuestionNumber()

                // Load next question
                setQuestion(questionList[questionCount].question)

                // Load next answers
                setAnswers(questionList)
            } else {
                submitRequest()
                loadLastActivity()
            }
        }

        answer3.setOnClickListener() {
            if (questionCount < 9) {
                // Save answer
                answerList.add(questionList[questionCount].answer[2])

                // Update the points of the player
                updatePoints(2, questionList)

                questionCount++
                setQuestionNumber()

                // Load next question
                setQuestion(questionList[questionCount].question)

                // Load next answers
                setAnswers(questionList)
            } else {
                submitRequest()
                loadLastActivity()
            }
        }

        answer4.setOnClickListener() {
            if (questionCount < 9) {
                // Save answer
                answerList.add(questionList[questionCount].answer[3])

                // Update the points of the player
                updatePoints(3, questionList)

                questionCount++
                setQuestionNumber()

                // Load next question
                setQuestion(questionList[questionCount].question)

                // Load next answers
                setAnswers(questionList)
            } else {
                submitRequest()
                loadLastActivity()
            }
        }
    }

    private fun submitRequest() {
        // ToDo send request -> send following parameters
        var timeLeftForReq = timeLeftInSeconds
        var listForReq = answerList
        //var idForReq = gameId
        //var nicknameForReq = nickname
    }


    private fun loadLastActivity() {
        val intent = Intent(this@QuestionActivity, LastActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val nrOfRightQuestions = numberOfRightQuestions.toString()
        intent.putExtra("nrOfRightQuestions", nrOfRightQuestions)
        intent.putExtra("nickname", nickname)
        // TODO send points of players
        // TODO send all questions and answers
        startActivity(intent)
    }

    private fun updatePoints(numberOfAnswer: Int, questionList: List<Question>) {
        val isAnswerCorrect = questionList[questionCount].answer[numberOfAnswer].isAnswerCorrect
        if (isAnswerCorrect) {
            numberOfRightQuestions++
            println("Points of the player: $numberOfRightQuestions\n")
        }
    }

    private fun setQuestionNumber() {
        val numberView = findViewById<TextView>(R.id.number)
        val questionNumber: Int = questionCount + 1
        numberView.text = "Frage Nummer: $questionNumber "
    }


}