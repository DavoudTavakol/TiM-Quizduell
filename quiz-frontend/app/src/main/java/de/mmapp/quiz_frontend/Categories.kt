package de.mmapp.quiz_frontend

import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

class Categories : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.gameid_screen)


        // Get the gameId and Categories from MainActivity
        val id = intent.getStringExtra("gameId")
        val categories = intent.getStringArrayListExtra("categories")

        // TO DO : Build the Checkbox list

        var gameId = findViewById<TextView>(R.id.gameId)
        gameId.text = id
        println(categories)


    }

    override fun onResume() {
        super.onResume()

    }


}