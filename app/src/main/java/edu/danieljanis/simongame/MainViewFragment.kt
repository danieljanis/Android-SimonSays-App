package edu.danieljanis.simongame


import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


class MainViewFragment : Fragment() {

    interface StateListener {
        fun onButtonPressed(buttonId: Int)
        fun getSequence(): MutableList<Int>
        fun getCurrentLevel(): SimonModel.Level
    }

    private lateinit var greenButton: Button
    private lateinit var redButton: Button
    private lateinit var blueButton: Button
    private lateinit var yellowButton: Button

    lateinit var listener: StateListener

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) {
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        greenButton = view.findViewById(R.id.greenButton)
        redButton = view.findViewById(R.id.redButton)
        blueButton = view.findViewById(R.id.blueButton)
        yellowButton = view.findViewById(R.id.yellowButton)

        greenButton.setOnClickListener {
            listener.onButtonPressed(greenButton.id)
        }
        redButton.setOnClickListener {
            listener.onButtonPressed(redButton.id)
        }
        blueButton.setOnClickListener {
            listener.onButtonPressed(blueButton.id)
        }
        yellowButton.setOnClickListener {
            listener.onButtonPressed(yellowButton.id)
        }
    }

    fun showSequence() {
        val startDelay = listener.getCurrentLevel().getButtonDelayTime()
        val animDuration = listener.getCurrentLevel().getButtonAnimationTime()

        val animationSequence = mutableListOf<Animator>()

        enableButtons(false)

        for (button in listener.getSequence()) {
            var objAnim = ObjectAnimator()

            when (button) {
                R.id.greenButton -> {
                    objAnim = ObjectAnimator.ofFloat(greenButton, "alpha", 0.5f, 1.0f)
                    objAnim.target = greenButton
                    objAnim.startDelay = startDelay
                    objAnim.duration = animDuration
                    animationSequence.add(objAnim)
                }
                R.id.redButton -> {
                    objAnim = ObjectAnimator.ofFloat(redButton, "alpha", 0.5f, 1.0f)
                    objAnim.target = redButton
                    objAnim.startDelay = startDelay
                    objAnim.duration = animDuration
                    animationSequence.add(objAnim)
                }
                R.id.blueButton-> {
                    objAnim = ObjectAnimator.ofFloat(blueButton, "alpha", 0.5f, 1.0f)
                    objAnim.target = blueButton
                    objAnim.startDelay = startDelay
                    objAnim.duration = animDuration
                    animationSequence.add(objAnim)
                }
                R.id.yellowButton -> {
                    objAnim = ObjectAnimator.ofFloat(yellowButton, "alpha", 0.5f, 1.0f)
                    objAnim.target = yellowButton
                    objAnim.startDelay = startDelay
                    objAnim.duration = animDuration
                    animationSequence.add(objAnim)
                }
            }
        }

        val animSet = AnimatorSet()
        animSet.playSequentially(animationSequence)
        animSet.duration = animDuration
        animSet.start()

        handler = Handler()
        runnable = Runnable {
            // When the animation is over, the buttons are enabled
            enableButtons(true)
        }
        val sequenceSize = listener.getSequence().size
        val delay = sequenceSize * animSet.duration + sequenceSize * startDelay
        handler.postDelayed(runnable, delay)
    }

    // disableButtons(false) = enabled them
    private fun enableButtons(enabled: Boolean) {
        greenButton.isEnabled = enabled
        redButton.isEnabled = enabled
        blueButton.isEnabled = enabled
        yellowButton.isEnabled = enabled
    }
}
