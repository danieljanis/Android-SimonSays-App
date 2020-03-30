package edu.danieljanis.simongame

import android.app.Application

class SimonApp: Application() {

    companion object {
        @Volatile
        private lateinit var instance: SimonApp

        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initScores()
    }

    // These scores will ALWAYS show up, even if I delete the file from the android phone itself,
    // which is what I wanted to happen
    fun initScores() {
        val scoreModel = ScoreModel()
        val scoresExist = scoreModel.loadScores() != null
        if (!scoresExist) {
            val scores: List<Score> = mutableListOf(
                Score(16),
                Score(7),
                Score(17),
                Score(6),
                Score(1),
                Score(19),
                Score(8),
                Score(20),
                Score(11),
                Score(13)
            )
            scores.forEach { scoreModel.saveScore(it) }
        }
    }
}