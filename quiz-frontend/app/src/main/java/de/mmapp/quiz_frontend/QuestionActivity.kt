package de.mmapp.quiz_frontend

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings.Global.getString
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mmapp.quiz_frontend.models.Answer
import de.mmapp.quiz_frontend.models.Game
import de.mmapp.quiz_frontend.models.GameStatus
import de.mmapp.quiz_frontend.models.Question
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

class QuestionActivity : AppCompatActivity() {

    private var questionCount: Int = 0
    private var answerList: MutableList<Answer> = mutableListOf()
    private var numberOfRightQuestions: Int = 0
    private var nickname: String = ""
    private var status : GameStatus = GameStatus.IN_PROGRESS

    // start of timer
    private var timeLeftInSeconds: Int = 60

    override fun onBackPressed() {
        println("Huhu")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions_activity)

        nickname = intent.getStringExtra("nickname").toString()
        println("This nickname is coming from the intent: $nickname")
        val card = findViewById<CardView>(R.id.card)
        card.setCardBackgroundColor(Color.rgb(240, 240, 240))
        card.cardElevation = 10.0F

        val anim = findViewById<LottieAnimationView>(R.id.animationView2)
        anim.visibility = View.INVISIBLE
        anim.setMinAndMaxFrame(137,280)


    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val game = intent.getParcelableExtra<Game>("game")
        println(game!!.questionList)

        // Init Question and answers
        setQuestionNumber()
        setQuestion(game.questionList[0].question)
        setAnswers(game.questionList,game.gameId)



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

                if (status == GameStatus.FINISHED){
                    cancel()

                }
            }

            override fun onFinish() {


                GlobalScope.launch(Dispatchers.Main) {
                    try {

                        if (status != GameStatus.FINISHED ){
                            val finishedGame = submitRequest(game.gameId,nickname,answerList,timeLeftInSeconds.toFloat())


                            showDefaultDialog(finishedGame)
                        }

                    } catch (e: IOException) {
                        println(e.message)
                        Toast.makeText(
                            this@QuestionActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
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


    private fun setAnswers(questionList: List<Question>, gameId: String) {

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
                setAnswers(questionList,gameId)
            } else {

                GlobalScope.launch(Dispatchers.Main) {

                    try {


                        val finishedGame = submitRequest(gameId,nickname,answerList,timeLeftInSeconds.toFloat())
                        println("&&&&&&&&&&&&&&&&&&&&&&&&&")
                        println(finishedGame.player2.answers)

                        showDefaultDialog(finishedGame)

                    } catch (e: IOException) {
                        println(e.message)
                        Toast.makeText(
                            this@QuestionActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


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
                setAnswers(questionList,gameId)
            } else {
                GlobalScope.launch(Dispatchers.Main) {

                    try {

                        val finishedGame = submitRequest(gameId,nickname,answerList,timeLeftInSeconds.toFloat())

                        println(finishedGame.player2.answers)
                        println("&&&&&&&&&&&&&&&&&&&&&&&&&")

                        showDefaultDialog(finishedGame)

                    } catch (e: IOException) {
                        println(e.message)
                        Toast.makeText(
                            this@QuestionActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
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
                setAnswers(questionList,gameId)
            } else {
                GlobalScope.launch(Dispatchers.Main) {


                    try {
                        val finishedGame = submitRequest(gameId,nickname,answerList,timeLeftInSeconds.toFloat())

                        println(finishedGame.player2.answers)
                        println("&&&&&&&&&&&&&&&&&&&&&&&&&")

                        showDefaultDialog(finishedGame)

                    } catch (e: IOException) {
                        println(e.message)
                        Toast.makeText(
                            this@QuestionActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
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
                setAnswers(questionList,gameId)
            } else {
                GlobalScope.launch(Dispatchers.Main) {

                    try {

                        val finishedGame = submitRequest(gameId,nickname,answerList,timeLeftInSeconds.toFloat())

                        println(finishedGame.player2.answers)

                        println("&&&&&&&&&&&&&&&&&&&&&&&&&")
                        showDefaultDialog(finishedGame)

                    } catch (e: IOException) {
                        println(e.message)
                        Toast.makeText(
                            this@QuestionActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    suspend fun submitRequest(gameId: String, nickname: String, answers: List<Answer>, time : Float): Game =
        GlobalScope.async(Dispatchers.IO) {


            val jsonBody = object  {
                var gameId = gameId
                var nickname = nickname
                var answers = answers
                var time = time
            }

            val mapper = jacksonObjectMapper()
            val body = mapper.writeValueAsString(jsonBody)

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(getString(R.string.submit_url))
                .post(body.toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()


            var game: Game
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val mapper = jacksonObjectMapper()

                game = mapper.readValue(response.body.string())
            }
            return@async game
        }.await()


    private fun loadLastActivity(game:Game) {

        val intent = Intent(this@QuestionActivity, LastActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val nrOfRightQuestions = numberOfRightQuestions.toString()
        intent.putExtra("nrOfRightQuestions", nrOfRightQuestions)
        intent.putExtra("nickname", nickname)
        intent.putExtra("score1",game.player1.score)
        intent.putExtra("score2",game.player2.score)

        var questions : ArrayList<String> = arrayListOf()

        println(game.questionList)

        for (i in game.questionList) {
            questions.add(i.question)
        }

        intent.putExtra("questions",questions)
        intent.putExtra("game",game)


        // TODO send points of players
        // TODO send all questions and answers
        startActivity(intent)
    }


    private fun showDefaultDialog(game: Game) {


        println(game.player2.score)
        println("=========SCORE============")
        status = GameStatus.FINISHED
        /*
        var alertDialog = MaterialAlertDialogBuilder(ContextThemeWrapper(this,R.style.MaterialAlertDialogRoundedStyle))
        val al = alertDialog.apply {
            setCancelable(false)
            setTitle("Fragen und Antworten")
            setMessage(answerList.toString())
        }.create()

        al.show()

         */

        val anim = findViewById<LottieAnimationView>(R.id.animationView2)
        val button1 = findViewById<Button>(R.id.answer1)
        val button2 = findViewById<Button>(R.id.answer2)
        val button3 = findViewById<Button>(R.id.answer3)
        val button4 = findViewById<Button>(R.id.answer4)


        button1.visibility = View.INVISIBLE
        button2.visibility = View.INVISIBLE
        button3.visibility = View.INVISIBLE
        button4.visibility = View.INVISIBLE

        anim.visibility = View.VISIBLE






        GlobalScope.launch {
            try {
                var finishedGame: Game

                (1..20).asFlow() // a flow of requests
                    .map { request ->
                        getGame(game!!.gameId,getString(R.string.get_game_url))
                    }
                    .collect { response ->

                        println("======================")
                        println(response.player1.answers)
                        println("======================")

                        if (response.player1.answers.isNotEmpty() && response.player2.answers.isNotEmpty()) {
                            //progressbar.visibility = View.INVISIBLE
                            finishedGame = response

                            //alertDialog.setCancelable(true)
                            //al.dismiss()
                            //anim.visibility = View.INVISIBLE

                            println(finishedGame.player1.answers)
                            println(finishedGame.player2.answers)

                            loadLastActivity(finishedGame)


                            // ToDo Am Besten Hier das UI Updaten
                            this.cancel()
                        }
                    }

            } catch (e :IOException){
                Toast.makeText(this@QuestionActivity, "Keine Verbindung", Toast.LENGTH_SHORT).show()
            }

        }

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
    companion object {
         suspend fun getGame(gameId : String, url : String) : Game = GlobalScope.async(Dispatchers.IO) {

             delay(1000)
            var game : Game
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .post(gameId.toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val mapper = jacksonObjectMapper()

                game = mapper.readValue(response.body.string())
            }
            return@async game

        }.await()

    }



}