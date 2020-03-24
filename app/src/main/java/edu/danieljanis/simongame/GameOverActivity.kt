package edu.danieljanis.simongame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game_over.*

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        startOverButton.setOnClickListener {
            val intent = Intent(this, DifficultyScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
