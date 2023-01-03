package de.mmapp.quiz_frontend

// by Irene

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HighscoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_screen)

        // display highscore table
        val table = findViewById<TextView>(R.id.top10)
        // TODO show top10 in highscore table

        // "Hauptmenue"
        val btnMenu = findViewById<Button>(R.id.menu)
        btnMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}