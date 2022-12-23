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
        setContentView(R.layout.highscore_screen)

        // display winner
        // for test run: instead of winner just players nickname
        val winner = findViewById<TextView>(R.id.whoWon)
        val nick = intent.getStringExtra("nickname")
        winner.setText(nick)

        // display your points
        val points = findViewById<TextView>(R.id.myPoints)
        points.setText("Deine Punkte: 'Zahl'")
        // TODO show real achieved points

        // display highscore table
        // 3 columns:
        // one column with rank, one with the "nickname" and one with the achieved score

        // test run
        val score = findViewById<TextView>(R.id.scoreBoard)
        score.setText("\n" + "1: " + "nick: " + "points" + "\n"
                + "2: " + "nick: " + "points" + "\n"
                + "3: " + "nick: " + "points" + "\n")

        /*
        val rank = findViewById<TextView>(R.id.rank)
        val name = findViewById<TextView>(R.id.name)
        val highscore = findViewById<TextView>(R.id.points)

        val row = findViewById<TableRow>(R.id.tableRow)
        // TODO show table with real content in real order
        // *add new row of content when other player finished game and order it
        // !just 5 rows in the table(?)
        */

        //"Erneut spielen"
        // for test run: instead of "Kategorien"-Screen use "Fragen"-Screen
        val buttonNewGame = findViewById<Button>(R.id.btn1)
        buttonNewGame.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }
        // TODO create intent to "Kategorie" screen for p1 and to waiting screen for p2

        // "Hauptmenue"
        val buttonMenu = findViewById<Button>(R.id.btn2)
        buttonMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}