package edu.danieljanis.simongame.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.danieljanis.simongame.persistence.daos.ScoreDao
import edu.danieljanis.simongame.persistence.entities.ScoreEntity

@Database(entities = [ScoreEntity::class], version = 1, exportSchema = false)
abstract class ScoreDatabase: RoomDatabase() {
    // This function returns the Scores Dao
    abstract fun scoreDao(): ScoreDao
}