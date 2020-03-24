package edu.danieljanis.simongame.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity hides the boiler-plate code, and we name the table hs_table
@Entity(tableName = "highscores")
data class ScoreEntity (
    // With each new row, SQLite increments id for us
    @PrimaryKey var id: Int,
    @ColumnInfo val score: String
)