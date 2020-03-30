package edu.danieljanis.simongame

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ScoreModel {

    companion object {
        const val SIMON_PREF_KEY = "simon.prefs"
        const val SCORES_PREF_KEY = "scores"
    }

    // Loads the scores and saves the new 'score' to the SharedPreferences file using Gson() ->
    //     Gson helps convert the scores list to JSON for easy storage
    fun saveScore(score: Score) {
        SimonApp.getInstance().getSharedPreferences(SIMON_PREF_KEY, Context.MODE_PRIVATE).edit().apply{
            val scores = loadScores() ?: mutableListOf()
            scores.add(score)
            val json = Gson().toJson(scores)
            putString("scores", json)
            apply()
        }
    }

    // Loads the stored scores from SharedPreferences and returns them
    fun loadScores(): MutableList<Score>? {
        SimonApp.getInstance().getSharedPreferences(SIMON_PREF_KEY, Context.MODE_PRIVATE).let {
            val json = it.getString(SCORES_PREF_KEY, null)
            return if (json.isNullOrEmpty()) null else Gson().fromJson(json, object: TypeToken<MutableList<Score>>(){}.type)
        }
    }

    // Grabs a sublist (top 10) and returns them in descending order (for populating the highscore screen)
    fun getTop10Scores(): List<Score> {
        val scores = loadScores() ?: mutableListOf()
        return scores.sortedWith(compareByDescending { it.score }).subList(0, 10)
    }
}