package edu.danieljanis.simongame

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start_screen.*

class DifficultyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        /*
        First screen to open with the difficulty buttons and a highscore button
        */

        easyButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("level", SimonModel.Level.EASY)
            startActivity(intent)
            finish()
        }

        mediumButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("level", SimonModel.Level.MEDIUM)
            startActivity(intent)
            finish()
        }

        hardButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("level", SimonModel.Level.HARD)
            startActivity(intent)
            finish()
        }

        highscoreButton.setOnClickListener {
            val intent = Intent(this, HighscoreActivity::class.java)
            startActivity(intent)
            // Does not finish() because I wanted the back button to be pressable here!
        }
    }
}
