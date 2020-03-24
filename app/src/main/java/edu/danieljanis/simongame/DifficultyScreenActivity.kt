package edu.danieljanis.simongame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_start_screen.*

class DifficultyScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        val context = this

        easyButton.setOnClickListener {
            val difficultyLevelInt: Int = 0
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("difficultyLevel", difficultyLevelInt)
            startActivity(intent)
        }

        mediumButton.setOnClickListener {
            val difficultyLevelInt: Int = 1
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("difficultyLevel", difficultyLevelInt)
            startActivity(intent)
        }

        hardButton.setOnClickListener {
            val difficultyLevelInt: Int = 2
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("difficultyLevel", difficultyLevelInt)
            startActivity(intent)
        }

        highscoreButton.setOnClickListener {
            val intent = Intent(context, HighScoreActivity::class.java)
            startActivity(intent)
        }
    }
}
