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

        easyButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("level", SimonModel.Level.EASY)
            startActivity(intent)
        }

        mediumButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("level", SimonModel.Level.MEDIUM)
            startActivity(intent)
        }

        hardButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("level", SimonModel.Level.HARD)
            startActivity(intent)
        }

        highscoreButton.setOnClickListener {
//            val intent = Intent(context, GameOverActivity::class.java)
//            startActivity(intent)
        }
    }
}
