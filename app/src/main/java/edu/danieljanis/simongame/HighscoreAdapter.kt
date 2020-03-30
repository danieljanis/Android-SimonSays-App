package edu.danieljanis.simongame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HighscoreAdapter(private val scores: List<Score>): RecyclerView.Adapter<HighscoreAdapter.HighscoreHolder>() {
    // Inflates the recyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighscoreHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_highscore, parent, false)
        return HighscoreHolder(view)
    }

    override fun getItemCount() = scores.size

    override fun onBindViewHolder(holder: HighscoreHolder, position: Int) {
        holder.bind(position)
    }

    inner class HighscoreHolder(view: View): RecyclerView.ViewHolder(view) {

        private val textScore: TextView = view.findViewById(R.id.scoreItemView)

        fun bind(position: Int) {
            val score = scores[position]
            textScore.text = score.score.toString()
        }
    }
}