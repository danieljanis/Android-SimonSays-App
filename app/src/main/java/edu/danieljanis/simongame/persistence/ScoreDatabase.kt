package edu.danieljanis.simongame.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.danieljanis.simongame.persistence.daos.ScoreDao
import edu.danieljanis.simongame.persistence.entities.Score

// I got help from the source below for this:
// https://gist.github.com/florina-muntenescu/697e543652b03d3d2a06703f5d6b44b5#file-datadatabase-kt-L29

@Database(entities = [Score::class], version = 1)
abstract class ScoreDatabase: RoomDatabase() {
    abstract fun scoreDao(): ScoreDao

    companion object {
        @Volatile private var INSTANCE: ScoreDatabase? = null

        fun getInstance(context: Context): ScoreDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ScoreDatabase::class.java, "Scores.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                            ioThread {
                                getInstance(context).scoreDao().insertScores(POPULATE_DATA)
                            }
                    }
                }
            ).build()

        val POPULATE_DATA = listOf(Score(0), Score(0), Score(0)
            , Score(0), Score(0), Score(0), Score(0), Score(0)
            , Score(0), Score(0)
        )
    }
}