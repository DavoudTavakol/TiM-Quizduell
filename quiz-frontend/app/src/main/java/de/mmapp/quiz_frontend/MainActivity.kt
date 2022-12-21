package de.mmapp.quiz_frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //"Neues Spiel"
        val buttonNewGame = findViewById<Button>(R.id.neuesSpiel)
        val eingabeE = findViewById<EditText>(R.id.nicknameEins)
        val eingabeZ = findViewById<EditText>(R.id.nicknameZwei)

        //TODO Button disabeln, wenn Feld leer
        buttonNewGame.setOnClickListener {
            setContentView(R.layout.gameid_screen)
            var nicknameEins = findViewById<TextView>(R.id.willkommenEins)
            val eingabeEins = eingabeE.text.toString()
            //check if the EditText has values or not
            if(eingabeEins.trim().length>0) {

            }else{
                Toast.makeText(applicationContext, "Please enter a nickname! ", Toast.LENGTH_SHORT).show()
            }
            nicknameEins.setText("Willkommen " + eingabeEins + "!\nLeite die Game ID an deinen Mitspieler weiter.")
        }

        // "Spiel beitreten"
        val buttonMenu = findViewById<Button>(R.id.spielBeitreten)
        buttonMenu.setOnClickListener {
            setContentView(R.layout.waiting_screen)
            var nicknameZwei = findViewById<TextView>(R.id.willkommenZwei)
            val eingabeZwei = eingabeZ.text.toString()
            nicknameZwei.setText("Willkommen " + eingabeZwei)
            //TODO Nickname 1 auch noch einbinden
            //something.setText(string + "wählt gerade die Kategorie. Bitte habe noch einen Moment Geduld, es geht gleich los!")
            //TODO Button disablen und nur wenn BEIDE Felder ausgefüllt sind, dann klickbar
        }
    }
}

