package de.mmapp.quiz_frontend

// by Irene Santana Martin

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mmapp.quiz_frontend.models.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

class LastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_screen)

        // display nickname of player
        val nameOfPlayer = intent.getStringExtra("nickname")
        val player = findViewById<TextView>(R.id.whoAreYou)
        player.setText("Danke fuers spielen " + nameOfPlayer + "!")

        // display nr of right questions answered
        val nrQ = intent.getStringExtra("nrOfRightQuestions")
        val rightQ = findViewById<TextView>(R.id.nrRightQ)
        rightQ.setText("Insgesamt " + nrQ + " von 10 Fragen rightig")

        // display your achieved points
        // val totalP = intent.getExtra(z)
        val points = findViewById<TextView>(R.id.myPoints)
        // points.setText("Punkte: " + totalP.toString())
        // TODO show REAL achieved points

        // display question list
        // val answeredQ = intent.putExtra(w)
        val list = findViewById<TextView>(R.id.qList)
        // make TextView scrollable
        list.movementMethod = ScrollingMovementMethod()
        // example text for testing of scrollbar
        list.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
        // list.setText(answeredQ.toString())
        // TODO show REAL full list of answered questions

        //"Erneut spielen" button on screen
        val btnNewGame = findViewById<Button>(R.id.btn1)
        btnNewGame.setOnClickListener {
            // redirect to category screen
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }
        // DISABLED AT THE MOMENT
        btnNewGame.isEnabled = false
        // TODO both players must press "Erneut spielen" to confirm that they are ready.
        //  (Player 1 presses Play again -> wait circle appears until player 2 also presses Play again (the other way round too)).

        // "Hauptmenue" button on screen
        val btnMenu = findViewById<Button>(R.id.btn2)
        btnMenu.setOnClickListener {
            // redirect to main screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // "Highscore Tabelle anzeigen" button on screen
        val btnHighscore = findViewById<Button>(R.id.btn3)
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
            .url("http://10.0.2.2:8085/highscore/getTopTen")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val mapper = jacksonObjectMapper()

            topTen = mapper.readValue(response.body.string())
        }
        return@async topTen
    }.await()

}