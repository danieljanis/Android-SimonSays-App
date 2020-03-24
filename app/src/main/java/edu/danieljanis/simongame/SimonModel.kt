package edu.danieljanis.simongame

class SimonModel {

    interface SimonListener {
        fun onInitializeSequence()
        fun onShowSequence()
        fun onCheckSequence()
        fun onIncrementSequence()
        fun onEndGame()
    }

    private var sequence = listOf<Int>()
    private var animationIndex = 0
    private var selectionIndex = 0

    lateinit var listener: SimonListener

    // start game
    fun initializeSequence() {
        // resetSequence()
    }

    fun showSequence() {

    }

    fun resetSequence() {
        sequence = listOf<Int>()
        // incrementSequence()
    }

    fun checkSequence(id: Int) {
        // check that the sequence at the selectionIndex is equal to id
        // if it is -> good
            // then, is it end of sequence?
                // if yes -> incrementSequence()
                // no -> user continues to play (do nothing code-wise unless have to)
        // else -> game over
    }

    fun incrementSequence() {
        // add randomly colored button onto sequence
    }

    fun endGame() {
        // Show highscore activity

        // resetSequence() --> save memory
        listener.onEndGame()
    }
}