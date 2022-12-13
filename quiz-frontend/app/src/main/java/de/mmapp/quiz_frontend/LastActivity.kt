package de.mmapp.quiz_frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_screen)

        //  Gewinner anzeige
        val winner = findViewById<TextView>(R.id.whoWon)
        winner.setText("'Nickname' hat gewonnen");

        // Punkte anzeige
        val points = findViewById<TextView>(R.id.myPoints)
        points.setText("Punkte: 'Zahl'")

        // "Highscore" Tabelle anzeige
        // 3 Spalten:
        // eine Spalte mit Rang, eine mit den "Nickname" und eine mit die erreichte Punktzahl
        // ...

        //"Erneut spielen"
        val buttonNewGame = findViewById<Button>(R.id.btn1)
        buttonNewGame.setOnClickListener {
            //val intent = Intent(this, //Kategorien Activity)
            //startActivity(intent)
        }

        // "Haputmenue"
        val buttonMenu = findViewById<Button>(R.id.btn2)
        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}