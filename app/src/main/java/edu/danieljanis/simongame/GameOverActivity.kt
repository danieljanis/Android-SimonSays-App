package edu.danieljanis.simongame

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game_over.*

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        /*
        GameOver screen which is shown when the user fails the game
        */

        // Gets the score from the intent
        // using the function openGameOverActivity() from MainActivity.kt
        val score = intent.getIntExtra("score", 0)
        val scoreText = findViewById<TextView>(R.id.currentScore)
        scoreText.text = score.toString()
        
        startOverButton.setOnClickListener {
            val intent = Intent(this, DifficultyActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {}
}
