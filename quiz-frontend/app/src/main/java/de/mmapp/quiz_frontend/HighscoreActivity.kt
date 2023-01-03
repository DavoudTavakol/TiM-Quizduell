package de.mmapp.quiz_frontend

// by Irene Santana Martin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.mmapp.quiz_frontend.models.Score

class HighscoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_screen)

        // display highscore table
        val topTen = intent.getParcelableArrayListExtra<Score>("topTen")
        val table = findViewById<TextView>(R.id.top10)
        table.setText(topTen?.toString())
        // TODO show position, nickname and score only

        // "Hauptmenue"
        // redirect to main screen
        val btnMenu = findViewById<Button>(R.id.menu)
        btnMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
