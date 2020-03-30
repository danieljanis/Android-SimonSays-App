package edu.danieljanis.simongame

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel

class SimonModel: ViewModel() {

    interface SimonListener {
        fun onStartGame()
        fun onShowSequence()
        fun onCheckSequence()
        fun onIncrementSequence()
        fun onEndGame()
    }

    enum class Level {
        EASY, MEDIUM, HARD;
        fun getButtonAnimationTime(): Long {
            return when (this) {
                EASY -> 1000
                MEDIUM -> 750
                HARD -> 400
            }
        }
        fun getButtonDelayTime(): Long {
            return when (this) {
                EASY -> 1000
                MEDIUM -> 750
                HARD -> 400
            }
        }
    }

    private var colorOptions = mutableListOf<@LayoutRes Int>()
    lateinit var level: Level
        private set
    lateinit var animSequence: MutableList<Int>
        private set
    lateinit var sequence: MutableList<Int>
        private set
    lateinit var checkSequence: MutableList<Int>
        private set
    private var selectionIndex = 0
    private var animationIndex = 0
    private var finalScore = 0
    lateinit var listener: SimonListener

    init {
        colorOptions.add(R.id.greenButton)
        colorOptions.add(R.id.redButton)
        colorOptions.add(R.id.blueButton)
        colorOptions.add(R.id.yellowButton)
    }

    fun setLevel(level: Level) {
        this.level = level
    }

    // When the game starts (a difficulty was chosen/passed) ->
    fun startGame() {
        animSequence = mutableListOf()
        sequence = mutableListOf()
        checkSequence = mutableListOf()
        incrementSequence()
        checkSequence = sequence.toMutableList()
        animSequence = sequence.toMutableList()
        listener.onStartGame()
    }

    private fun showSequence() {
        listener.onShowSequence()
    }


    // Returns true if the user enters the correct sequence,
    // Returns false if the game ended
    fun checkSequence(buttonId: Int): Boolean {
        return if (matchesAtIndex(buttonId)) {
            selectionIndex++
            animationIndex = selectionIndex
            // If the right button was clicked and it was the end of the sequence,
            // add a new random button and show the new sequence
            if (selectionIndex >= animSequence.size) {
                finalScore++
                resetSelectionIndex()
                incrementSequence()
            }
            // increments the score on the screen
            listener.onCheckSequence()
            true
        } else {
            // The game is over, user has lost
            endGame()
            false
        }
    }

    //  checkIfEmpty() checks if the sequence is empty, if it isn't,
    //  it removes the index of the sequence (there should only be 1 index)
    fun checkIfEmpty() {
        if (checkSequence.isNotEmpty()) {
            checkSequence.removeAt(0)
            animSequence = checkSequence
        }
    }

    private fun matchesAtIndex(buttonId: Int): Boolean {
        return sequence[selectionIndex] == buttonId
    }

    private fun resetSelectionIndex() {
        selectionIndex = 0
    }

    // New random button gets added to the sequence and stored (into a sequence which is temporary
    // and one which contains the entire sequence to be checked)
    private fun incrementSequence() {
        resetSelectionIndex()
        sequence.add(colorOptions[(0..3).random()])
        animSequence = sequence.toMutableList()
        showSequence()
    }

    fun getCurrentButton(): Int {
        return sequence[selectionIndex]
    }

    // When the game has ended, this function gets called and will onEndGame will flash the correct
    // button 3 times (the button which should have been pressed) and then the ScoreModel (highscore list)
    // will get updated with the finalScore of the current game, which was the score the user had when
    // the game ended.
    private fun endGame() {
        listener.onEndGame()
        ScoreModel().saveScore(Score(getScore()))
    }

    fun getScore(): Int {
        return finalScore
    }
}