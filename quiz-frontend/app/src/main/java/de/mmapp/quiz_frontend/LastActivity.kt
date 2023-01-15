package de.mmapp.quiz_frontend

// by Irene Santana Martin

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mmapp.quiz_frontend.models.Game
import de.mmapp.quiz_frontend.models.Score
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

class LastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_screen)

        var game = intent.getParcelableExtra<Game>("game")

        // display nickname of player
        val nameOfPlayer = intent.getStringExtra("nickname")
        val player = findViewById<TextView>(R.id.whoAreYou)
        // display on screen
        player.setText("Danke fürs spielen " + nameOfPlayer)

        // display nr of right questions answered
        val nrQ = intent.getStringExtra("nrOfRightQuestions")
        val rightQ = findViewById<TextView>(R.id.nrRightQ)
        rightQ.setText("Insgesamt " + nrQ + " von 10 Fragen richtig")

        // display your achieved points
        val totalP = intent.getStringExtra("p2".toString())
        val myPoints = findViewById<TextView>(R.id.myPoints)
        // myPoints.setText("Punkte: " + game!!.player2.score)
        myPoints.setText("Punkte: " + totalP)
        // TODO show REAL achieved pointsç

        // display question list
        val answeredQ = intent.getStringExtra("answers")
        val list = findViewById<TextView>(R.id.qList)
        // make TextView scrollable
        list.movementMethod = ScrollingMovementMethod()
        // display in screen
        list.setText(answeredQ)

        // "Hauptmenue" button on screen
        val btnMenu = findViewById<Button>(R.id.btn1)
        btnMenu.setOnClickListener {
            // redirect to main screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // "Highscore Tabelle anzeigen" button on screen
        val btnHighscore = findViewById<Button>(R.id.btn2)
        btnHighscore.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    // get highscores from DB
                    val topTen = getHighscoreTable()
                    // redirect to highscore screen
                    val intent = Intent(this@LastActivity, HighscoreActivity::class.java)

                    // send values to "HighscoreActivity"
                    intent.putExtra("topTen", topTen)

                    startActivity(intent)
                } catch (e: IOException) {
                    Toast.makeText(this@LastActivity, "Keine Verbindung", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // get highscores from DB
    // request
    suspend fun getHighscoreTable(): ArrayList<Score> = GlobalScope.async(Dispatchers.IO) {

        var topTen: ArrayList<Score> = arrayListOf()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(getString(R.string.highscore_url))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val mapper = jacksonObjectMapper()

            topTen = mapper.readValue(response.body.string())
        }
        return@async topTen
    }.await()

}
