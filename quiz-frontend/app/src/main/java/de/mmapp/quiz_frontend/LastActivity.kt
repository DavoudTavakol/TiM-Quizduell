package de.mmapp.quiz_frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class LastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_screen)

        // display winner
        val winner = findViewById<TextView>(R.id.whoWon)
        winner.setText("'Nickname' hat gewonnen");
        // TODO show real nickname of winner
        // who was previously written in main screen

        // display your points
        val points = findViewById<TextView>(R.id.myPoints)
        points.setText("Deine Punkte: 'Zahl'")
        // TODO show real achieved points

        // display highscore table
        // 3 columns:
        // one column with rank, one with the "nickname" and one with the achieved score
        val rank = findViewById<TextView>(R.id.rank)
        val name = findViewById<TextView>(R.id.name)
        val highscore = findViewById<TextView>(R.id.points)
        // TODO show table with real content in real order

        //"Erneut spielen"
        val buttonNewGame = findViewById<Button>(R.id.btn1)
        buttonNewGame.setOnClickListener {
            //val intent = Intent(this, //Kategorien Activity)
            //startActivity(intent)
        }
        // TODO create intent to "Kategorie" screen of p1 and to waiting screen for p2

        // "Hauptmenue"
        val buttonMenu = findViewById<Button>(R.id.btn2)
        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}