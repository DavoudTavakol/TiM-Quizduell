package de.mmapp.quiz_frontend

// by Irene

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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
        val btnMenu = findViewById<Button>(R.id.btn2)
        btnMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // "Highscore Tabelle anzeigen"
        val btnHighscore = findViewById<Button>(R.id.btn3)
        btnHighscore.setOnClickListener {
            val intent = Intent(this, HighscoreActivity::class.java)
            startActivity(intent)
        }
    }
}