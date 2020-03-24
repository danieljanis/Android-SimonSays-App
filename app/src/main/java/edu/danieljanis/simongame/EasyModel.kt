package edu.danieljanis.simongame

// THIS IS THE MODEL

import android.os.Handler
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class EasyModel: ViewModel() {

    var listener: Listener? = null
    interface Listener {
        fun sequenceTriggered()
    }

    var isRunning = false
    private set

    private var handler: Handler? = null

    fun startSequence(integer: Int) {
        var delay = 1000
        if (integer == 0) {
            delay = 1000
        }
        else if (integer == 1) {
            delay = 750
        }
        else if (integer == 2) {
            delay = 400
        }

        // Game starts, 1.2 seconds before the first flash
        if (handler == null) {
            handler = Handler()
            handler?.postDelayed(runnable, 1200)
            isRunning = true
        }
    }

    fun checkSequence(buttonId: Int) {

    }

    fun resetSequence() {
        handler?.removeCallbacks(runnable)
        isRunning = false
        handler = null
    }

    // Runnable runs it once, then calls triggerSequence (runs infinitely)
    private var runnable: Runnable = Runnable {
        listener?.sequenceTriggered()
    }
}