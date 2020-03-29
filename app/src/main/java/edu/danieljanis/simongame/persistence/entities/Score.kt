package edu.danieljanis.simongame.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class Score(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "highScore")
    var storeScore: Int? = null
)