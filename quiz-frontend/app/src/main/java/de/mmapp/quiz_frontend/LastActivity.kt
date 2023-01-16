package de.mmapp.quiz_frontend

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mmapp.quiz_frontend.models.Game
import de.mmapp.quiz_frontend.models.Score
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

class LastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_screen)

        // all vars/vals
        // from previous activity
        val game = intent.getParcelableExtra<Game>("game")
        val nameOfPlayer = intent.getStringExtra("nickname")
        val nrQ = intent.getStringExtra("nrOfRightQuestions")
        var score1 = intent.getIntExtra("score1", 0)
        var score2 = intent.getIntExtra("score2", 0)
        val questions = intent.getStringArrayListExtra("questions")

        // display winner
        val winner = findViewById<TextView>(R.id.whoWon)
        // create String to save the values of nickname of winner
        var name: String = ""
        // define winner by comparing scores
        if (score1 > score2) {
            name = game!!.player1.nickname
        } else {
            name = game!!.player2.nickname
        }
        // display on screen
        winner.setText("[" + name + "] hat gewonnen!")

        // display nickname of current player
        val player = findViewById<TextView>(R.id.whoAreYou)
        player.setText("Deine Ergebnisse [" + nameOfPlayer + "]:")

        // display nr of right questions answered
        val rightQ = findViewById<TextView>(R.id.nrRightQ)
        rightQ.setText("Insgesamt " + nrQ + " von 10 Fragen richtig")

        // display your achieved points
        val points = findViewById<TextView>(R.id.myPoints)
        // create Int to save the values
        var pPoints: Int = 0
        // show points of right player
        if (nameOfPlayer == game.player2.nickname){
            pPoints = score2
        } else if (nameOfPlayer == game.player1.nickname) {
            pPoints = score1
        }
        // display on screen
        points.setText("Punkte: " + pPoints)

        // display question list
        val listView = findViewById<TextView>(R.id.qList)
        // make TextView scrollable
        listView.movementMethod = ScrollingMovementMethod()
        // display on screen
        listView.setText("Fragenliste: \n" + questions.toString())

        // "Hauptmenue" button on screen
        val btnMenu = findViewById<Button>(R.id.btn1)
        btnMenu.setOnClickListener {
            // redirect to main screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // ensure to close the previous activity before starting the new one
            // all variables will be reset when the activity is restarted
            finish()
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
                    intent.putExtra("topTen",topTen)

                    startActivity(intent)

                    // ensure to close the previous activity before starting the new one
                    // all variables will be reset when the activity is restarted
                    finish()
                } catch (e : IOException)  {
                    Toast.makeText(this@LastActivity, "Keine Verbindung", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // get highscores from DB
    // request
    suspend fun getHighscoreTable() : ArrayList<Score> = GlobalScope.async(Dispatchers.IO) {

        var topTen : ArrayList<Score> = arrayListOf()
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
