package de.mmapp.quiz_frontend

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mmapp.quiz_frontend.CategoriesActivity.Companion.checkIfReady
import de.mmapp.quiz_frontend.QuestionActivity.Companion.getGame
import de.mmapp.quiz_frontend.models.Game
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

class MainActivity : AppCompatActivity() {



    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // New Game
        val buttonNewGame = findViewById<Button>(R.id.newGame)
        val inputPlayerOne = findViewById<EditText>(R.id.nicknameOne)

        inputPlayerOne.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, counJot: Int
            ) {
                if (s.isNotEmpty()) {
                    val obligatory = findViewById<View>(R.id.obligatoryNicknameOne)
                    obligatory.visibility = View.INVISIBLE

                    buttonNewGame.isEnabled = true
                    buttonNewGame.setOnClickListener {
                        val nick = inputPlayerOne.text.toString()
                        var gameid: String = ""

                        GlobalScope.launch(Dispatchers.Main) {


                            try {

                                gameid = createGameRequest(inputPlayerOne.text.toString())
                                println(gameid)

                                var categories: ArrayList<String> =
                                    getCategories() as ArrayList<String>
                                val intent =
                                    Intent(this@MainActivity, CategoriesActivity::class.java)

                                // Send gameId and Categories List to Categories Activity
                                intent.putExtra("nickname", nick)
                                intent.putExtra("gameId", gameid)
                                intent.putStringArrayListExtra("categories", categories)

                                println(categories)
                                startActivity(intent)
                            } catch (e: IOException) {
                                println(e.message)
                                Toast.makeText(
                                    this@MainActivity,
                                    e.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        print(gameid)
                    }
                } else {
                    buttonNewGame.isEnabled = false
                    val obligatory = findViewById<View>(R.id.obligatoryNicknameOne)
                    obligatory.visibility = View.VISIBLE
                }
            }
        })

        // Join Game
        val buttonJoinGame = findViewById<Button>(R.id.joinGame)
        val inputPlayerTwo = findViewById<EditText>(R.id.nicknameTwo)
        val inputId = findViewById<EditText>(R.id.gameID)
        var boolID = false
        var boolNick = false

        fun waitingScreen(game: Game) {
            setContentView(R.layout.waiting_screen)
            var nicknameTwo = findViewById<TextView>(R.id.greetingTwo)
            val nicknamePlTwo = inputPlayerTwo.text.toString()
            var text = findViewById<TextView>(R.id.passGameId)
            nicknameTwo.text = "Willkommen " + nicknamePlTwo + "!"
            text.text = game.player1.nickname + " wählt gerade die Kategorien. \nBitte habe noch einen Moment Geduld, es geht gleich los!"


            val anim = findViewById<LottieAnimationView>(R.id.animationView2)
            anim.visibility = View.VISIBLE
            anim.setMinAndMaxFrame(137,280)
            // Polling : Asking the server every second if the other player is ready.
            // checkIfReady is a static method of the class CategoriesActivity
            var newGame: Game
            GlobalScope.launch() {

                newGame = setReady(game.player2.nickname, game.gameId, mutableListOf(),getString(R.string.set_ready_url))

                (1..30).asFlow() // a flow of requests
                    .map { request -> checkIfReady(game.gameId, game.player2.nickname, getString(R.string.is_ready_url)) }
                    .collect { response ->


                        if (response.isReady && response.questions.isNotEmpty()) {
                            val intent =
                                Intent(this@MainActivity, QuestionActivity::class.java)
                            game.questionList = response.questions
                            intent.putExtra("game", game)
                            intent.putExtra("nickname", nicknamePlTwo)
                            startActivity(intent)

                            this.cancel()
                        }
                    }
            }
        }

        fun update() {
            if (boolID && boolNick) {
                buttonJoinGame.isEnabled = true
                buttonJoinGame.setOnClickListener {

                    var game: Game?

                    GlobalScope.launch(Dispatchers.Main) {

                        848846
                        try {
                            game = connectToGameRequest(
                                inputPlayerTwo.text.toString(),
                                inputId.text.toString()
                            )
                            //println(game)
                            if (game!!.gameId == "0"){
                                println("Nickname Used By Player1")
                                var obligatory = findViewById<TextView>(R.id.obligatoryNicknameTwo)
                                obligatory.text = "Nickname bereits vom Gegner benutzt."
                                obligatory.visibility = View.VISIBLE
                            } else {
                                println(game)
                                waitingScreen(game!!)
                            }
                            //
                            //081188
                        } catch (e: IOException) {


                            println(e)
                                Toast.makeText(
                                    this@MainActivity,
                                    e.localizedMessage.toString(),
                                    Toast.LENGTH_LONG
                                ).show()


                            //TextView wird eingeblendet, wenn Nickname1 = Nickname 2 //funktioniert noch nicht

                            }

                    }
                }
            } else {
                buttonJoinGame.isEnabled = false
            }
        }

        inputPlayerTwo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) {
                    boolNick = true
                    update()
                    val obligatory = findViewById<View>(R.id.obligatoryNicknameTwo)
                    obligatory.visibility = View.INVISIBLE
                } else {
                    boolNick = false
                    update()
                    val obligatory = findViewById<View>(R.id.obligatoryNicknameTwo)
                    obligatory.visibility = View.VISIBLE
                }
            }
        })

        inputId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length == 6) {
                    boolID = true
                    val obligatory = findViewById<View>(R.id.obligatoryGameId)
                    obligatory.visibility = View.INVISIBLE
                    update()
                } else {
                    boolID = false
                    val obligatory = findViewById<View>(R.id.obligatoryGameId)
                    obligatory.visibility = View.VISIBLE
                    update()
                }
            }
        })
    }

    suspend fun createGameRequest(nickname: String): String = GlobalScope.async(Dispatchers.IO) {
        // Request Body , See Documentation

        val jsonBody: String = """
             {
                 "nickname" : "$nickname"
             }
             
         """.trimIndent()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(resources.getString(R.string.create_url))
            .post(jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType()))
            .build()

        var gameId: String = ""

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val mapper = jacksonObjectMapper()

            // Parse the JSON String into a Game instance (accessibility through game. )
            var game: Game = mapper.readValue(response.body.string())
            println(game)
            println(game.gameId)
            gameId = game.gameId

        }
        return@async gameId
    }.await()

    private suspend fun connectToGameRequest(nickname: String, gameId: String): Game? =
        GlobalScope.async(Dispatchers.IO) {

            val jsonBody: String = """
             {
                 "gameId": "$gameId",
                 "player2" : {
                    "nickname" : "$nickname"
                 }
             }
             
         """.trimIndent()

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(getString(R.string.connect_url))
                .post(jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()

            var game: Game
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val mapper = jacksonObjectMapper()
                game = mapper.readValue(response.body.string())

            }
            return@async game
        }.await()


    companion object {
        suspend fun setReady(nickname: String, gameId: String, categories: MutableList<String>, url : String): Game =
            GlobalScope.async(Dispatchers.IO) {

                val jsonBody = object  {
                    var gameId = gameId
                    var nickname = nickname
                    var categories = categories
                }

                val mapper = jacksonObjectMapper()
                val body = mapper.writeValueAsString(jsonBody)

                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(url)
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
    }

    private suspend fun getCategories(): List<String> = GlobalScope.async(Dispatchers.IO) {

        var categories: List<String> = listOf()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(getString(R.string.categories_url))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val mapper = jacksonObjectMapper()

            categories = mapper.readValue(response.body.string())
        }
        return@async categories
    }.await()
}
