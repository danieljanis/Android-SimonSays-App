package edu.danieljanis.simongame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_game_over.*
import kotlinx.android.synthetic.main.fragment_main_view.*

class MainActivity : AppCompatActivity(), MainViewFragment.StateListener {
    private var mainViewFragment: MainViewFragment? = null

    private lateinit var simonModel: SimonModel

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        val level = intent.getSerializableExtra("level") as SimonModel.Level

        simonModel = ViewModelProvider(this).get(SimonModel::class.java)
        simonModel.setLevel(level)
        simonModel.listener = object: SimonModel.SimonListener {
            override fun onStartGame() {}

            override fun onShowSequence() {
                mainViewFragment?.showSequence()
            }

            override fun onCheckSequence() {
                scoreTextView.text = simonModel.getScore().toString()
            }

            override fun onIncrementSequence() {}

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

    override fun onResume() {
        super.onResume()
        simonModel.startGame()
    }

    private fun openGameOverActivity() {
        val intent = Intent(this@MainActivity, GameOverActivity::class.java)
        intent.putExtra("score", simonModel.getScore())
        startActivity(intent)
        finish()
    }

    override fun onButtonPressed(buttonId: Int) {
        flashButton(buttonId, 1000)
        simonModel.checkSequence(buttonId)
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

    private fun flashButton(buttonId: Int, duration: Long) {
        val flashAnim: Animation = AlphaAnimation(0.5f, 1.0f)
        flashAnim.duration = duration
        flashAnim.repeatCount = 0
        findViewById<Button>(buttonId).startAnimation(flashAnim)
    }

    private fun flashCorrectButton(buttonId: Int, duration: Long, startDelay: Long) {
        flashButton(buttonId, duration)

        handler = Handler()
        runnable = Runnable {
            openGameOverActivity()
        }
        val delay = duration + startDelay
        handler.postDelayed(runnable, delay)
    }
}
