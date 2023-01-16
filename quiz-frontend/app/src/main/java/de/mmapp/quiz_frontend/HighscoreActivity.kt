package de.mmapp.quiz_frontend

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

        var positionInTop = findViewById<TextView>(R.id.pos)
        var nicknameOfPlayer = findViewById<TextView>(R.id.nick)
        var totalScore = findViewById<TextView>(R.id.sco)

        // display position
        // create String to save the values of positions
        var tmpPos = ""
        // create int to give nr of position
        var n: Int = 1
        // add position one after the other in column 1
        for (i in topTen!!.subList(0,10)) {
            tmpPos += "$n." + "\n"
            n+=1
        }
        // display in screen
        positionInTop.setText(tmpPos)

        // display nickname
        // create String to save the values of nicknames
        var tmpNick = ""
        // add nicknames one after the other in column 2
        for (i in topTen!!.subList(0,10)) {
            tmpNick += i.nickname + "\n"
        }
        // display in screen
        nicknameOfPlayer.setText(tmpNick)

        // display score
        // create String to save the values of scores
        var tmpSco = ""
        // add scores one after the other in column 3
        for (i in topTen!!.subList(0,10)) {
            tmpSco += i.score.toString() + "\n"
        }
        // display in screen
        totalScore.setText(tmpSco)

        // "Hauptmenue" button on screen
        val btnMenu = findViewById<Button>(R.id.menu)
        btnMenu.setOnClickListener {
            // redirect to main screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // ensure to close the previous activity before starting the new one
            // all variables will be reset when the activity is restarted
            finish()
        }
    }
}
