package edu.danieljanis.simongame


import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_start_screen.view.*
import kotlinx.android.synthetic.main.fragment_main_view.*
import kotlinx.android.synthetic.main.fragment_main_view.view.*
import java.util.*

class MainViewFragment : Fragment() {

    // Interface
    interface StateListener {
        fun greenButtonPressed()
        fun redButtonPressed()
        fun blueButtonPressed()
        fun yellowButtonPressed()
        fun resetButtonPressed()
    }

    var listener: StateListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // THIS IS THE VIEW

        val view = inflater.inflate(R.layout.fragment_main_view, container, false)

        view.greenButton.setOnClickListener {
            listener?.greenButtonPressed()
        }

        view.redButton.setOnClickListener {
            listener?.redButtonPressed()
        }

        view.blueButton.setOnClickListener {
            listener?.blueButtonPressed()
        }

        view.yellowButton.setOnClickListener {
            listener?.yellowButtonPressed()
        }

        view.restartButton.setOnClickListener {
            listener?.resetButtonPressed()
        }

        return view
    }

    fun runUIUpdate(integer: Int) {
        var delay = 1000
        when (integer) {
            0 -> {
                delay = 1000
            }
            1 -> {
                delay = 750
            }
            2 -> {
                delay = 400
            }
        }
        activity?.let {activity ->
            val colorList = arrayListOf<String>()
            var score = 0

            val view = when ((0..3).random()) {
                0 -> greenButton
                1 -> redButton
                2 -> blueButton
                3 -> yellowButton
                else -> restartButton
            }

            val originalName = view.toString()
            colorList.add(originalName)
            Log.e("TAG", colorList.toString())
            val originalColor = view.background as? ColorDrawable
            val whiteColor = ContextCompat.getColor(activity, R.color.colorWhite)
            val animator = ValueAnimator.ofObject(
                ArgbEvaluator(),
                originalColor?.color,
                whiteColor,
                originalColor?.color
            )

            animator.addUpdateListener { valueAnimator ->
                (valueAnimator.animatedValue as? Int)?.let { animatedValue ->
                    view.setBackgroundColor(animatedValue)
                }
            }

            animator?.startDelay = (delay).toLong()
            return@let animator?.start()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainViewFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
