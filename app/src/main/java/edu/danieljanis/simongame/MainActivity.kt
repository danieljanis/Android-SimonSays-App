package edu.danieljanis.simongame

// THIS IS THE CONTROLLER

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_start_screen.*

class MainActivity : AppCompatActivity(), SimonModel.SimonListener() {


    private var viewFragment: MainViewFragment? = null

    private lateinit var easyModel: EasyModel
//    private lateinit var mediumModel: MediumModel
//    private lateinit var hardModel: HardModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)


        // Difficulty integer determines how hard the game is:
        /*
        *       0 = Easy
        *       1 = Medium
        *       2 = Hard
         */
        val intent = intent
        val integer = intent.getIntExtra("difficultyLevel", 0)
        Log.e("TAG", "Difficulty integer -> $integer")

        easyModel = ViewModelProvider(this).get(EasyModel::class.java)


//        mediumModel = ViewModelProvider(this).get(MediumModel::class.java)
//        hardModel = ViewModelProvider(this).get(HardModel::class.java)

        viewFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? MainViewFragment
        if (viewFragment == null) {
            viewFragment = MainViewFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, viewFragment!!)
                .commit()
        }

        viewFragment?.listener = object: MainViewFragment.StateListener {
            override fun greenButtonPressed() {
                Log.e("TAG", "Green button pressed (Delegated from the View to the Controller)")
                easyModel.checkSequence(integer)
            }

            override fun redButtonPressed() {
                Log.e("TAG", "Red button pressed (Delegated from the View to the Controller)")
                easyModel.checkSequence(integer)
            }

            override fun blueButtonPressed() {
                Log.e("TAG", "Blue button pressed (Delegated from the View to the Controller)")
                easyModel.checkSequence(integer)
            }

            override fun yellowButtonPressed() {
                Log.e("TAG", "Yellow button pressed (Delegated from the View to the Controller)")
                easyModel.checkSequence(integer)
            }

            override fun resetButtonPressed() {
                Log.e("TAG", "Reset button pressed (Delegated from the View to the Controller)")
                easyModel.resetSequence()
            }
        }

        easyModel.listener = object: EasyModel.Listener {
            override fun sequenceTriggered() {
                Log.e("TAG", "Delegated from the EasyModel to the Controller")
                viewFragment?.runUIUpdate(integer)
            }
        }
    }

    override fun onInitializeSequence() {
        
    }

    override fun onShowSequence() {

    }

    override fun onCheckSequence() {

    }

    override fun onIncrementSequence() {

    }

    override fun onEndGame() {

    }
}
