package de.mmapp.quiz_frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mmapp.quiz_frontend.models.Game
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

class CategoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.gameid_screen)
        // Get the gameId and Categories from MainActivity
        val id = intent.getStringExtra("gameId")
        val categories = intent.getStringArrayListExtra("categories")

        // TO DO : Build the Checkbox list

        var gameId = findViewById<TextView>(R.id.gameId)
        val nickname = findViewById<TextView>(R.id.willkommenEins)
        gameId.text = id
        nickname.text = intent.getStringExtra("nickname")
        println(categories)


    }

    override fun onResume() {
        super.onResume()

        val startButton = findViewById<Button>(R.id.startButton)
        val id = intent.getStringExtra("gameId")
        val nickname = intent.getStringExtra("nickname")


        startButton.setOnClickListener() {

            GlobalScope.launch(){

                val game = MainActivity.setReady(nickname!!,id!!)

               // Check is the Player2 is Ready
                (1..30).asFlow() // a flow of requests
                    .map { request -> checkIfReady(id!!,nickname!!) }
                    .collect { response ->

                        println(response)
                        if (response == "true"){
                            val intent = Intent(this@CategoriesActivity, QuestionActivity::class.java)
                            intent.putExtra("game",game)
                            startActivity(intent)

                            this.cancel()

                        }
                    }


                    }
            }



    }


    companion object{
        suspend fun checkIfReady(gameId : String, nickname : String): String = GlobalScope.async(Dispatchers.IO) {

            delay(1000)
            val jsonBody :String = """
             {
                 "gameId": "$gameId",
                  "nickname" : "$nickname"
             }
             
         """.trimIndent()

            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://10.0.2.2:8085/game/check")
                .post(jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()

            var game : String


            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val mapper = jacksonObjectMapper()

                game = mapper.readValue(response.body.string())



            }


            return@async game


        }.await()

    }




}



