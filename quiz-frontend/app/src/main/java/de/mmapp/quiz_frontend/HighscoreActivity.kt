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

        // highscore table
        val topTen = intent.getParcelableArrayListExtra<Score>("topTen")
        val table = findViewById<TextView>(R.id.top10)
        // create String to save the values
        var text = "\n"
        // create int to give nr of position
        var x: Int = 1
        // get the top ten scores one after the other on separate rows
        // where [position: nickname: score]
        for (i in topTen!!) {
            text += "$x" + " : " + i.nickname + " : " + i.score + "\n"
            x+=1
        }
        // display in screen
        table.setText(text)
        // TODO layout

        // "Hauptmenue" button on screen
        val btnMenu = findViewById<Button>(R.id.menu)
        btnMenu.setOnClickListener {
            // redirect to main screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
