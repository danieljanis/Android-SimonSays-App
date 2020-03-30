package edu.danieljanis.simongame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HighscoreActivity: AppCompatActivity() {

    private val scoreModel = ScoreModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)

        // Builds the highscore RecyclerView using getTop10Scores() -> check ScoreModel.kt for info
        val recyclerView: RecyclerView = findViewById(R.id.highscoreRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HighscoreAdapter(scoreModel.getTop10Scores())
    }
}