package de.mmapp.quiz_frontend

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.android.material.progressindicator.CircularProgressIndicator
import de.mmapp.quiz_frontend.models.CheckResponse
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

    var countCategories = 0;

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gameid_screen)

        var ll_game_layout = findViewById<LinearLayout>(R.id.ll_game_layout)

        // Get the gameId and Categories from MainActivity
        val id = intent.getStringExtra("gameId")
        var categories = arrayListOf<String>()
        categories = intent.getStringArrayListExtra("categories") as ArrayList<String>

        for(category: String in categories){
            val checkbox = CheckBox(ContextThemeWrapper(this, R.style.MyChechBox))
            checkbox.text = category
            checkbox.id = countCategories
            countCategories++
            checkbox.setTextColor(Color.WHITE)
            checkbox.textSize = 22F

            // add TextView to LinearLayout
             ll_game_layout.addView(checkbox)
        }

        var gameId = findViewById<TextView>(R.id.gameId)
        //val nickname = findViewById<TextView>(R.id.greetingOne)
        gameId.text = id
        //nickname.text = intent.getStringExtra("nickname")
        val name = intent.getStringExtra("nickname")
        var text = findViewById<TextView>(R.id.passGameId)
        text.text = "Willkommen " + name + "!  Bitte leite die ID  weiter."
        text.textSize = 25F
        println(categories)
    }

    //Auswertung der geklickten Kategorien
    fun getCheckedCategroies(): MutableList<String> {

        var ll_game_layout = findViewById<LinearLayout>(R.id.ll_game_layout)
        var count = ll_game_layout.childCount
        var arrayCount = 0

        //Checkboxen anschauen, welche angemerkt sind und dann dem returnValue zufügen
        for (i in count downTo 1 step 1) {
            var v = ll_game_layout.getChildAt(i-1) as CheckBox
            if (v.isChecked)
                arrayCount++

        }

        var returnValue : MutableList<String> = mutableListOf()
        var arraycountUp = 0

        for (i in count downTo 1 step 1) {
            var v = ll_game_layout.getChildAt(i-1) as CheckBox
            if (v.isChecked){
                returnValue.add(v.text.toString())
                arraycountUp++
            }
        }
        return returnValue
    }

    override fun onResume() {
        super.onResume()

        val startButton = findViewById<Button>(R.id.startButton)
        val id = intent.getStringExtra("gameId")
        val nickname = intent.getStringExtra("nickname")

        val progressbar = findViewById<ProgressBar>(R.id.requestProgress)

        progressbar.isIndeterminate = true
        progressbar.visibility = View.INVISIBLE




        startButton.setOnClickListener() {

            progressbar.visibility = View.VISIBLE
            //Bei Klick auf Start werden die gewählten Checkboxen ausgewertet und in einem String Array gespeichert
            var clickedCategoriesArray = getCheckedCategroies()


            GlobalScope.launch() {

                val game = MainActivity.setReady(nickname!!, id!!,clickedCategoriesArray,getString(R.string.set_ready_url))

                // Check is the Player2 is Ready
                (1..30).asFlow() // a flow of requests
                    .map { request -> checkIfReady(id!!, nickname!!, getString(R.string.is_ready_url)) }
                    .collect { response ->

                        if (response.isReady && response.questions.isNotEmpty()) {
                            val intent =
                                Intent(this@CategoriesActivity, QuestionActivity::class.java)
                            game.questionList = response.questions
                            intent.putExtra("game", game)
                            intent.putExtra("nickname", nickname)
                            //progressbar.visibility = View.INVISIBLE
                            startActivity(intent)

                            this.cancel()
                        }
                    }
            }
        }
    }

    companion object {
        suspend fun checkIfReady(gameId: String, nickname: String, url : String): CheckResponse =
            GlobalScope.async(Dispatchers.IO) {

                delay(1000)
                val jsonBody: String = """
             {
                 "gameId": "$gameId",
                  "nickname" : "$nickname"
             }
             
         """.trimIndent()

                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(url)
                    .post(jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType()))
                    .build()

                var checkRespnse: CheckResponse

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val mapper = jacksonObjectMapper()

                    checkRespnse = mapper.readValue(response.body.string())
                }
                return@async checkRespnse
            }.await()
    }
}



