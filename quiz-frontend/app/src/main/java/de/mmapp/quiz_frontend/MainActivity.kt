package de.mmapp.quiz_frontend

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mmapp.quiz_frontend.CategoriesActivity.Companion.checkIfReady
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
                    buttonNewGame.isEnabled = true
                    buttonNewGame.setOnClickListener {
                        val nick = inputPlayerOne.text.toString()
                        // TODO when ready, change from "LastActivity" to "QuestionActivity"

                        //setContentView(R.layout.gameid_screen) /löschen?

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
                                Toast.makeText(
                                    this@MainActivity,
                                    "Keine Verbindung",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        print(gameid)
                    }
                } else {
                    buttonNewGame.isEnabled = false
                    Toast.makeText(
                        applicationContext,
                        "Du musst einen Nicknamen eingeben! ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        // Join Game
        val buttonJoinGame = findViewById<Button>(R.id.joinGame)
        val inputPlayerTwo = findViewById<EditText>(R.id.nicknameTwo)
        val inputId = findViewById<EditText>(R.id.gameID)
        var boolID = false
        var boolNick = false
        //TODO Check if Nickname 1 und Nickname 2 are equal

        fun waitingScreen(game: Game) {
            setContentView(R.layout.waiting_screen)
            var nicknameTwo = findViewById<TextView>(R.id.greetingTwo)
            val nicknamePtwo = inputPlayerTwo.text.toString()
            var text = findViewById<TextView>(R.id.passGameId)
            nicknameTwo.text = "Willkommen " + nicknamePtwo
            text.text = game.player1.nickname + " wählt gerade die Kategorien. \nBitte habe noch einen Moment Geduld, es geht gleich los!"

            // Polling : Asking the server every second if the other player is ready.
            // checkIfReady is a static method of the class CategoriesActivity

            var newGame: Game
            GlobalScope.launch() {

                newGame = setReady(game.player2.nickname, game.gameId, mutableListOf())

                (1..30).asFlow() // a flow of requests
                    .map { request -> checkIfReady(game.gameId, game.player2.nickname) }
                    .collect { response ->

                        println(response)
                        if (response == "true") {
                            val intent = Intent(this@MainActivity, QuestionActivity::class.java)

                            intent.putExtra("game", newGame)
                            intent.putExtra("nickname", nicknamePtwo)
                            startActivity(intent)
                            this.cancel()

                        }
                    }
            }
        }

        854062
        fun update() {
            if (boolID && boolNick) {
                buttonJoinGame.isEnabled = true
                buttonJoinGame.setOnClickListener {

                    var game: Game


                    GlobalScope.launch(Dispatchers.Main) {

                        try {
                            game = connectToGameRequest(
                                inputPlayerTwo.text.toString(),
                                inputId.text.toString()
                            )
                            println(game)
                            waitingScreen(game)
                        } catch (e: IOException) {

                                Toast.makeText(
                                    this@MainActivity,
                                    e.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
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
                } else {
                    boolNick = false
                    update()
                    Toast.makeText(
                        applicationContext,
                        "Du musst einen Nicknamen eingeben! ",
                        Toast.LENGTH_SHORT
                    ).show()
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
                    update()
                } else {
                    boolID = false
                    update()
                    if (s.isEmpty()) {
                        Toast.makeText(
                            applicationContext,
                            "Du musst eine 6-stellige Game ID eingeben! ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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
            .url("http://10.0.2.2:8085/game/create")
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

    private suspend fun connectToGameRequest(nickname: String, gameId: String): Game =
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
                .url("http://10.0.2.2:8085/game/connect")
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
        suspend fun setReady(nickname: String, gameId: String, categories: MutableList<String>): Game =
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
                    .url("http://10.0.2.2:8085/game/ready")
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
            .url("http://10.0.2.2:8085/game/categories")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val mapper = jacksonObjectMapper()

            categories = mapper.readValue(response.body.string())
        }
        return@async categories
    }.await()
}




