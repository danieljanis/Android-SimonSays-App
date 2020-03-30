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
import kotlinx.android.synthetic.main.fragment_main_view.*


class MainViewFragment : Fragment() {

    interface StateListener {
        fun onButtonPressed(buttonId: Int): Boolean
        fun getSequence(): MutableList<Int>
        fun getCurrentLevel(): SimonModel.Level
        fun onCheckIfEmpty()
    }

    private lateinit var greenButton: Button
    private lateinit var redButton: Button
    private lateinit var blueButton: Button
    private lateinit var yellowButton: Button

    lateinit var listener: StateListener

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var currentAnimator: Animator? = null

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
            listener.onButtonPressed(greenButton.id).also {
                if (!it) enableButtons(false)
            }
        }
        redButton.setOnClickListener {
            listener.onButtonPressed(redButton.id).also {
                if (!it) enableButtons(false)
            }
        }
        blueButton.setOnClickListener {
            listener.onButtonPressed(blueButton.id).also {
                if (!it) enableButtons(false)
            }
        }
        yellowButton.setOnClickListener {
            listener.onButtonPressed(yellowButton.id).also {
                if (!it) enableButtons(false)
            }
        }
    }

    /*
    showSequence() is the bread and butter, it has two listeners:
        1. startDelay (how long to wait for the animation to start)
            and
        2. animDuration (how long the animation will last)

        animationSequence is a list of the current sequence and used to play
            the animations sequentially (explained further down)

    Firstly, buttons are disabled when showSequence() starts its magic,
        for each button in the sequence, create an object (objAnim),
            based on which button was chosen, do the following:
                1. Add details (Button, propertyName, startDelay, animDuration)
                2. set target = Button
                3. set delay = startDelay
                4. set duration = animDuration
                5. onAnimationEnd, using function onCheckIfEmpty() from MainActivity.kt,
                    (which calls checkIfEmpty() from SimonModel.kt)
                6. Finally, the objAnim is stored in the animationSequence
    */
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
                    objAnim.addListener(object: Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) {}
                        override fun onAnimationRepeat(animation: Animator?) {}
                        override fun onAnimationCancel(animation: Animator?) {}
                        override fun onAnimationEnd(animation: Animator?) {
                            listener.onCheckIfEmpty()
                        }
                    })
                    animationSequence.add(objAnim)
                }
                R.id.redButton -> {
                    objAnim = ObjectAnimator.ofFloat(redButton, "alpha", 0.5f, 1.0f)
                    objAnim.target = redButton
                    objAnim.startDelay = startDelay
                    objAnim.duration = animDuration
                    objAnim.addListener(object: Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) {}
                        override fun onAnimationRepeat(animation: Animator?) {}
                        override fun onAnimationCancel(animation: Animator?) {}
                        override fun onAnimationEnd(animation: Animator?) {
                            listener.onCheckIfEmpty()
                        }
                    })
                    animationSequence.add(objAnim)
                }
                R.id.blueButton-> {
                    objAnim = ObjectAnimator.ofFloat(blueButton, "alpha", 0.5f, 1.0f)
                    objAnim.target = blueButton
                    objAnim.startDelay = startDelay
                    objAnim.duration = animDuration
                    objAnim.addListener(object: Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) {}
                        override fun onAnimationRepeat(animation: Animator?) {}
                        override fun onAnimationCancel(animation: Animator?) {}
                        override fun onAnimationEnd(animation: Animator?) {
                            listener.onCheckIfEmpty()
                        }
                    })
                    animationSequence.add(objAnim)
                }
                R.id.yellowButton -> {
                    objAnim = ObjectAnimator.ofFloat(yellowButton, "alpha", 0.5f, 1.0f)
                    objAnim.target = yellowButton
                    objAnim.startDelay = startDelay
                    objAnim.duration = animDuration
                    objAnim.addListener(object: Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) {}
                        override fun onAnimationRepeat(animation: Animator?) {}
                        override fun onAnimationCancel(animation: Animator?) {}
                        override fun onAnimationEnd(animation: Animator?) {
                            listener.onCheckIfEmpty()
                        }
                    })
                    animationSequence.add(objAnim)
                }
            }
        }

        // This applys the following to the currentAnimator,
        // Plays the animationSequence in order, grabbing the duration each time and
        // start() begins the sequence animation
        currentAnimator = AnimatorSet().apply {
            playSequentially(animationSequence)
            duration = animDuration
            start()
        }

        // handler is bound to a Looper, used to deliver the following runnable to the Looper's
        // message queue and to execute them on the Looper's thread
        handler = Handler()
        runnable = Runnable {
            enableButtons(true)
            if (tempTextView.text != null) {
                tempTextView.text = getString(R.string.playersTurn)
            }
        }
        tempTextView.text = getString(R.string.simonsTurn)
        val sequenceSize = listener.getSequence().size
        val delay = sequenceSize * currentAnimator!!.duration + sequenceSize * startDelay
        // Easy way to delay the runnable
        handler.postDelayed(runnable, delay)
    }

    // enabledButtons(true) = clickable buttons
    private fun enableButtons(enabled: Boolean) {
        greenButton.isEnabled = enabled
        redButton.isEnabled = enabled
        blueButton.isEnabled = enabled
        yellowButton.isEnabled = enabled
    }
}
