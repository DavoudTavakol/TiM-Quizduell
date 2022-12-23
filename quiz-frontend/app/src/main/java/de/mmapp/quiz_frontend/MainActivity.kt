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
import de.mmapp.quiz_frontend.models.Game
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Neues Spiel
        val buttonNewGame = findViewById<Button>(R.id.neuesSpiel)
        val eingabeE = findViewById<EditText>(R.id.nicknameEins)

        eingabeE.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, counJot: Int
            ) {
                if (s.isNotEmpty()) {
                    buttonNewGame.isEnabled = true
                    buttonNewGame.setOnClickListener {
                        val nick = eingabeE.text.toString()
                        // TODO when ready, change from "LastActivity" to "QuestionActivity"

                        //setContentView(R.layout.gameid_screen)

                        var gameid: String = ""

                        GlobalScope.launch(Dispatchers.Main) {

                            // Post Request to Start Game
                            gameid = createGameRequest("mo")
                            println(gameid)

                            var categories : ArrayList<String> = getCategories() as ArrayList<String>
                            val intent = Intent(this@MainActivity, Categories::class.java)

                            // Send gameId and Categories List to Categories Activity
                            intent.putExtra("gameId", gameid)
                            intent.putStringArrayListExtra("categories", categories)

                            println(categories)
                            startActivity(intent)
                        }



                        print(gameid)




                    }
                } else {
                    buttonNewGame.isEnabled = false
                    Toast.makeText(applicationContext, "Du musst einen Nicknamen eingeben! ", Toast.LENGTH_SHORT).show()
                }
            }
        })

        // Spiel beitreten
        val buttonJoinGame = findViewById<Button>(R.id.spielBeitreten)
        val eingabeZ = findViewById<EditText>(R.id.nicknameZwei)
        val eingabeID = findViewById<EditText>(R.id.gameID)
        var boolID = false
        var boolNick = false
        // TODO Nickname 1
        // val textZ = findViewById<TextView>(R.id.spEinsWahlKategorien)

        fun waitingScreen(nickname1 : String){
            setContentView(R.layout.waiting_screen)
            var nicknameZwei = findViewById<TextView>(R.id.willkommenZwei)
            val eingabeZwei = eingabeZ.text.toString()
            var textZ = findViewById<TextView>(R.id.spEinsWahlKategorien)
            nicknameZwei.text = "Willkommen " + eingabeZwei
            //TODO QuestionsActivity starten
            //TODO Nickname 1 auch noch einbinden
            textZ.setText(nickname1 + "wählt gerade die Kategorie. Bitte habe noch einen Moment Geduld, es geht gleich los!")
        }

        fun update (){
            if(boolID && boolNick){
                buttonJoinGame.isEnabled = true
                buttonJoinGame.setOnClickListener {

                    var game : Game
                    GlobalScope.launch(Dispatchers.Main) {
                        game = connectToGameRequest("mee", "735115")
                        println(game)


                        waitingScreen(game.player1.nickname)


                    }



                }
            } else {
                buttonJoinGame.isEnabled = false
            }
        }

        eingabeZ.addTextChangedListener(object : TextWatcher {
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
                    Toast.makeText(applicationContext, "Du musst einen Nicknamen eingeben! ", Toast.LENGTH_SHORT).show()
                }
            }
        })

        eingabeID.addTextChangedListener(object : TextWatcher {
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
                        Toast.makeText(applicationContext, "Du musst eine 6-stellige Game ID eingeben! ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


     suspend fun createGameRequest(nickname : String): String = GlobalScope.async(Dispatchers.IO) {


       // Request Body , See Documentation

        val jsonBody :String = """
             {
                 "nickname" : "$nickname"
             }
             
         """.trimIndent()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8085/game/create")
            .post(jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType()))
            .build()

        var gameId : String = ""


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


    private suspend fun connectToGameRequest(nickname : String, gameId : String): Game = GlobalScope.async(Dispatchers.IO) {

        val jsonBody :String = """
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

        var game : Game


        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val mapper = jacksonObjectMapper()

            game = mapper.readValue(response.body.string())



        }


        return@async game


    }.await()


    private suspend fun getCategories() : List<String> = GlobalScope.async(Dispatchers.IO) {

        var categories : List<String> = listOf()
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




