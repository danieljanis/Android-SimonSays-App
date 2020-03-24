package edu.danieljanis.simongame.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import edu.danieljanis.simongame.persistence.entities.ScoreEntity

@Dao
interface ScoreDao {
    @Insert
    fun insertScores(scores: List<ScoreEntity>)

    //fun insertScores(vararg scores: ScoreEntity)
}