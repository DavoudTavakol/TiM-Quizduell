package de.mmapp.quiz_frontend

// by Irene Santana Martin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.mmapp.quiz_frontend.models.Score
import org.w3c.dom.Text

class HighscoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_screen)

        val topTen = intent.getParcelableArrayListExtra<Score>("topTen")

        // highscore view
        val table = findViewById<TextView>(R.id.top10)
        // create String to save the values
        var text = "\nPosition : Nickname : Score\n"
        // create int to give nr of position
        var x: Int = 1
        // get the top ten scores one after the other on separate rows
        // where [position: nickname: score]
        for (i in topTen!!) {
            text += "$x." + " : " + i.nickname + " : " + i.score + "\n"
            x+=1
        }
        // display in screen
        table.setText(text)


        // highscore table
        var positionInTop = findViewById<TextView>(R.id.pos)
        var nicknameOfPlayer = findViewById<TextView>(R.id.nick)
        var totalScore = findViewById<TextView>(R.id.sco)

        // display position
        // create String to save the values of positions
        var tmpPos = ""
        // create int to give nr of position
        var n: Int = 1
        // add position one after the other in column 1
        for (i in topTen!!) {
            tmpPos += "$n." + "\n"
            n+=1
        }
        // display in screen
        positionInTop.setText(tmpPos)

        // display nickname
        // create String to save the values of nicknames
        var tmpNick = ""
        // add nicknames one after the other in column 2
        for (i in topTen!!) {
            tmpNick += i.nickname + "\n"
        }
        // display in screen
        nicknameOfPlayer.setText(tmpNick)

        // display score
        // create String to save the values of scores
        var tmpSco = ""
        // add scores one after the other in column 3
        for (i in topTen!!) {
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
        }
    }
}
