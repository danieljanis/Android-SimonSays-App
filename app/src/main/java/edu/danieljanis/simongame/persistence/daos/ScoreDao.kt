package edu.danieljanis.simongame.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.danieljanis.simongame.persistence.entities.Score

@Dao
interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertScores(Scores: List<Score>)
    @Query(value = "SELECT highScore FROM Scores ORDER BY highScore")
    fun getHighScores(): List<Score>
}