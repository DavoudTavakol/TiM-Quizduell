package de.mmapp.quiz_frontend

// by Irene Santana Martin

import android.content.Intent
import android.os.Bundle
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

        // display winner
        val winner = findViewById<TextView>(R.id.whoWon)
        // TODO show winners real nickname

        // display nr of right questions answered
        val rightQ = findViewById<TextView>(R.id.nrRightQ)
        // TODO show real nr of right questions player got

        // display your achieved points
        val points = findViewById<TextView>(R.id.myPoints)
        // TODO show real achieved points

        // display question list
        val list = findViewById<TextView>(R.id.qList)

        //"Erneut spielen"
        val btnNewGame = findViewById<Button>(R.id.btn1)
        btnNewGame.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }
        // TODO both players must press "Erneut spielen" to confirm that they are ready.
        //  (Player 1 presses Play again -> wait circle appears until player 2 also presses Play again (the other way round too)).

        // "Hauptmenue"
        // redirect to main screen
        val btnMenu = findViewById<Button>(R.id.btn2)
        btnMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // "Highscore Tabelle anzeigen"
        // redirect to highscore screen
        val btnHighscore = findViewById<Button>(R.id.btn3)
        btnHighscore.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    // get highscores from DB
                    val topTen = getHighscoreTable()
                    val intent = Intent(this@LastActivity, HighscoreActivity::class.java)

                    intent.putExtra("topTen",topTen)

                    startActivity(intent)
                } catch (e : IOException)  {
                    Toast.makeText(this@LastActivity, "Keine Verbindung", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

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