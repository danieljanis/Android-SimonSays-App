package edu.danieljanis.simongame.persistence

import android.app.Activity
import android.content.Context
import androidx.room.Room
import edu.danieljanis.simongame.Score
import edu.danieljanis.simongame.persistence.entities.ScoreEntity

class ScoreRepository(private var ctx: Context) {
    private val db: ScoreDatabase

    init {
        // Makes sure the context is the App Context (NOT activity context)
        if (ctx is Activity) {
            ctx = ctx.applicationContext
        }
        db = Room.databaseBuilder(ctx, ScoreDatabase::class.java, "score.sqlite")
            .build()
    }

    fun saveScores(scores: List<Score>) {
        val entities = scores.map {
            ScoreEntity(it.id, it.score)
        }
        // Inserts scores using the Score and ScoreEntity classes
        db.scoreDao().insertScores(entities)
    }
}