package edu.danieljanis.simongame

import org.json.JSONObject

data class Score(val id: Int, val score: String) {
    constructor(scoreObject: JSONObject): this(
        id = scoreObject.getInt("id"),
        score = scoreObject.getString("score")
    )
}