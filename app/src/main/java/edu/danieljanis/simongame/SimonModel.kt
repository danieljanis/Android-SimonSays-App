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
    lateinit var sequence: MutableList<Int>
        private set
    private var animationIndex = 0
    private var selectionIndex = 0
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

    fun startGame() {
        sequence = mutableListOf()
        incrementSequence()
        listener.onStartGame()
    }

    fun showSequence() {
        listener.onShowSequence()
    }

    fun checkSequence(buttonId: Int) {
        // check that the sequence at the selectionIndex is equal to id
        if (matchesAtIndex(buttonId)) {
            if (endOfSequence()) {
                incrementSequence()
            }
            selectionIndex++
        }
        else {
            endGame()
        }
        listener.onCheckSequence()
    }

    fun matchesAtIndex(buttonId: Int): Boolean {
        return sequence[selectionIndex] == buttonId
    }

    fun endOfSequence(): Boolean {
        return selectionIndex == sequence.size - 1
    }

    fun incrementSequence() {
        // add randomly colored button onto sequence
        sequence.add(colorOptions[(0..3).random()])
        showSequence()
        listener.onIncrementSequence()
    }

    fun getCurrentButton(): Int {
        return sequence[selectionIndex]
    }

    fun endGame() {
        // Show highscore activity

        // resetSequence() --> save memory
        listener.onEndGame()
    }

    fun getScore(): Int {
        // could be +1
        return selectionIndex
    }
}