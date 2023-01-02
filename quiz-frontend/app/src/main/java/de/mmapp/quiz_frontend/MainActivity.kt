package de.mmapp.quiz_frontend
// by Christine
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Neues Spiel
        val buttonNewGame = findViewById<Button>(R.id.neuesSpiel)
        val eingabeE = findViewById<EditText>(R.id.nicknameEins)

        eingabeE.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, counJot: Int
            ) {
                if (s.isNotEmpty()) {
                    buttonNewGame.isEnabled = true
                    buttonNewGame.setOnClickListener {
                        val nick = eingabeE.text.toString()
                        // TODO when ready, change from "LastActivity" to "QuestionActivity"
                        val intent = Intent(this@MainActivity, Question::class.java)
                        intent.putExtra("nickname", nick)
                        startActivity(intent)
                    }
                } else {
                    buttonNewGame.isEnabled = false
                    Toast.makeText(applicationContext, "Du musst einen Nicknamen eingeben! ", Toast.LENGTH_SHORT).show()
                }
            }
        })

        // Spiel beitreten
        val buttonJoinGame = findViewById<Button>(R.id.spielBeitreten)
        val eingabeZ = findViewById<EditText>(R.id.nicknameZwei)
        val eingabeID = findViewById<EditText>(R.id.gameID)
        var boolID = false
        var boolNick = false
        // TODO Nickname 1
        // val textZ = findViewById<TextView>(R.id.spEinsWahlKategorien)

        fun waitingScreen(){
            setContentView(R.layout.waiting_screen)
            var nicknameZwei = findViewById<TextView>(R.id.willkommenZwei)
            val eingabeZwei = eingabeZ.text.toString()
            nicknameZwei.text = "Willkommen " + eingabeZwei
            //TODO QuestionsActivity starten
            //TODO Nickname 1 auch noch einbinden
            //textZ.setText(Nickname1 + "wählt gerade die Kategorie. Bitte habe noch einen Moment Geduld, es geht gleich los!")
            //findViewById<RelativeLayout>(R.id.loadingPanel).setVisibility(View.GONE);
        }

        fun update (){
            if(boolID && boolNick){
                buttonJoinGame.isEnabled = true
                buttonJoinGame.setOnClickListener {
                    waitingScreen()
                }
            } else {
                buttonJoinGame.isEnabled = false
            }
        }

        eingabeZ.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) {
                    boolNick = true
                    update()
                } else {
                    boolNick = false
                    update()
                    Toast.makeText(applicationContext, "Du musst einen Nicknamen eingeben! ", Toast.LENGTH_SHORT).show()
                }
            }
        })

        eingabeID.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length == 6) {
                    boolID = true
                    update()
                } else {
                    boolID = false
                    update()
                    if (s.isEmpty()) {
                        Toast.makeText(applicationContext, "Du musst eine 6-stellige Game ID eingeben! ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}


