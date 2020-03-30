package edu.danieljanis.simongame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_main_view.*

class MainActivity : AppCompatActivity(), MainViewFragment.StateListener {
    private var mainViewFragment: MainViewFragment? = null

    private lateinit var simonModel: SimonModel

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        /*
        MainActivity (the game screen)
            This screen pops up when the easy, medium, or hard button is pressed
            and the game begins when onResume() is called
        */

        // Gets the difficulty level from the SimonModel and passes it via intent from DifficultyActivity
        val level = intent.getSerializableExtra("level") as SimonModel.Level

        simonModel = ViewModelProvider(this).get(SimonModel::class.java)
        simonModel.setLevel(level)
        simonModel.listener = object: SimonModel.SimonListener {
            override fun onStartGame() {}

            override fun onShowSequence() {
                // This right here is where the animation sequence comes from
                // onShowSequence() is utilized in the SimonModel.kt as the function showSequence()
                //      Every time this is called, a user has entered a correct sequence
                mainViewFragment?.showSequence()
            }

            // Sets the score on the screen as the game is running and the user enters a
            // correct sequence, increments the score by 1
            //      (used in SimonModel.kt, checkSequence() function)
            override fun onCheckSequence() {
                scoreTextView.text = simonModel.getScore().toString()
            }

            override fun onIncrementSequence() {}

            // Flashes the button that should have been pressed when the user failed to enter
            // the correct sequence.
            override fun onEndGame() {
                flashCorrectButton(simonModel.getCurrentButton(), simonModel.level.getButtonAnimationTime(), simonModel.level.getButtonDelayTime())
            }
        }

        mainViewFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? MainViewFragment
        if (mainViewFragment == null) {
            mainViewFragment = MainViewFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, mainViewFragment!!)
                .commit()
        }
        mainViewFragment?.listener = this
    }

    override fun onBackPressed() {
        val intent = Intent(this, DifficultyActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        simonModel.startGame()
    }

    // Passes the final score to the GameOverActivity
    private fun openGameOverActivity() {
        val intent = Intent(this@MainActivity, GameOverActivity::class.java)
        intent.putExtra("score", simonModel.getScore())
        startActivity(intent)
        finish()
    }

    override fun onButtonPressed(buttonId: Int): Boolean {
        flashButton(buttonId, 1000, 0)
        return simonModel.checkSequence(buttonId)
    }

    override fun getSequence(): MutableList<Int> {
        return simonModel.sequence
    }

    override fun getCurrentLevel(): SimonModel.Level {
        return simonModel.level
    }

    override fun onCheckIfEmpty() {
        simonModel.checkIfEmpty()
    }

    private fun flashButton(buttonId: Int, duration: Long, repeatCount: Int) {
        val flashAnim: Animation = AlphaAnimation(0.5f, 1.0f)
        flashAnim.duration = duration
        flashAnim.repeatCount = repeatCount
        findViewById<Button>(buttonId).startAnimation(flashAnim)
    }

    // When the user failed to enter the correct sequence this is called
    private fun flashCorrectButton(buttonId: Int, duration: Long, startDelay: Long) {
        flashButton(buttonId, duration, 2)

        // handler is bound to a Looper, used to deliver the following runnable to the Looper's
        // message queue and to execute them on the Looper's thread
        handler = Handler()
        runnable = Runnable {
            openGameOverActivity()
        }
        val delay = duration + startDelay
        // Delays this by 2 so that it can show the entire animation
        handler.postDelayed(runnable, delay * 2)
    }
}
