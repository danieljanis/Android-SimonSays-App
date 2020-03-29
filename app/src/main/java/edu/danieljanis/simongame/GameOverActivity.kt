package edu.danieljanis.simongame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import edu.danieljanis.simongame.persistence.ScoreDatabase.Companion.POPULATE_DATA
import edu.danieljanis.simongame.persistence.entities.Score
import kotlinx.android.synthetic.main.activity_game_over.*

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val currentScore = findViewById<TextView>(R.id.currentScore)
        val endScore1 = findViewById<TextView>(R.id.endScore1)
        val endScore2 = findViewById<TextView>(R.id.endScore2)
        val endScore3 = findViewById<TextView>(R.id.endScore3)
        val endScore4 = findViewById<TextView>(R.id.endScore4)
        val endScore5 = findViewById<TextView>(R.id.endScore5)
        val endScore6 = findViewById<TextView>(R.id.endScore6)
        val endScore7 = findViewById<TextView>(R.id.endScore7)
        val endScore8 = findViewById<TextView>(R.id.endScore8)
        val endScore9 = findViewById<TextView>(R.id.endScore9)
        val endScore10 = findViewById<TextView>(R.id.endScore10)


        val storeScore = 0
        val score = intent.getIntExtra("score", storeScore)
        currentScore.text = score.toString()

        val highscore: MutableList<Score> = POPULATE_DATA.toMutableList()
        //val highscore: List<Score> = getInstance(this).scoreDao().getHighScores()


//        if (score >= 0) {
////            endScore1.text = score.toString()
////        }

        var count = 0
        for (index in 0..9) {
            if (score > highscore[count].storeScore!!) {
                val difference: Int = (9 - index)
                var total: Int = 9
                for (newIndex in 1..difference) {
                    highscore[total] = highscore[total - 1]
                    total--
                }
                //highscore[count].storeScore = storeScore
                highscore[count].storeScore = score
                break
            }
            count++
        }

        for (index in 0 until 9) {
            endScore1.text = highscore[0].storeScore.toString()
            endScore2.text = highscore[1].storeScore.toString()
            endScore3.text = highscore[2].storeScore.toString()
            endScore4.text = highscore[3].storeScore.toString()
            endScore5.text = highscore[4].storeScore.toString()
            endScore6.text = highscore[5].storeScore.toString()
            endScore7.text = highscore[6].storeScore.toString()
            endScore8.text = highscore[7].storeScore.toString()
            endScore9.text = highscore[8].storeScore.toString()
            endScore10.text = highscore[9].storeScore.toString()
        }
        Log.e("TAG", "highscores: $highscore")

        startOverButton.setOnClickListener {
            val intent = Intent(this, DifficultyScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
