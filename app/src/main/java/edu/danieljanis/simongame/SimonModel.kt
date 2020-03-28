package edu.danieljanis.simongame

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import kotlin.math.absoluteValue

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

    fun startGame() {
        animSequence = mutableListOf()
        sequence = mutableListOf()
        checkSequence = mutableListOf()
        incrementSequence()
        checkSequence = sequence.toMutableList()
        animSequence = sequence.toMutableList()
        listener.onStartGame()
    }

    fun showSequence() {
        listener.onShowSequence()
    }

    fun checkSequence(buttonId: Int) {
        if (matchesAtIndex(buttonId)) {
            selectionIndex++
            animationIndex = selectionIndex
            if (selectionIndex >= animSequence.size) {
                finalScore++
                resetSelectionIndex()
                incrementSequence()
            }
            listener.onCheckSequence()
        }
        else {
            endGame()
        }
    }

    fun checkIfEmpty() {
        if (checkSequence.isNotEmpty()) {
            checkSequence.removeAt(0)
            animSequence = checkSequence
        }
    }

    fun matchesAtIndex(buttonId: Int): Boolean {
        return sequence[selectionIndex] == buttonId
    }

    fun resetSelectionIndex() {
        selectionIndex = 0
    }

    fun incrementSequence() {
        resetSelectionIndex()
        // add randomly colored button onto sequence
        sequence.add(colorOptions[(0..3).random()])
        animSequence = sequence.toMutableList()
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
        return finalScore
    }
}