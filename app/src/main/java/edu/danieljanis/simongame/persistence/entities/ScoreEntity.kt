package edu.danieljanis.simongame.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.jetbrains.annotations.NotNull

// @Entity hides the boiler-plate code, and we name the table hs_table
@Entity(tableName = "highscores")
data class ScoreEntity (
    // With each new row, SQLite increments id for us
    @NotNull @PrimaryKey val id: Int,
    @NotNull val score: String
)