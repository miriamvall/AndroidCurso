package com.curso.androidcurso.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import com.curso.androidcurso.dialogs.AddDialog
import com.curso.androidcurso.MainActivity
import com.curso.androidcurso.R
import com.curso.androidcurso.model.StatsApi
import com.curso.androidcurso.dialogs.ConfirmExitDialog
import com.curso.androidcurso.model.GestorPartidasDB
import com.curso.androidcurso.model.Partida
import com.google.android.material.snackbar.Snackbar
import okhttp3.*
import java.text.SimpleDateFormat
import java.util.*

private const val NUEVA_PARTIDA = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentGame : Fragment() {

    // components
    private lateinit var chronometer: Chronometer
    private lateinit var startButton: Button
    private lateinit var endButton: Button
    private lateinit var imageViews: List<ImageView>
    private lateinit var sharedPref: SharedPreferences
    // game
    private lateinit var cardBacks: List<Int>
    private lateinit var completedImages: BooleanArray
    private var flippedCardIndex: Int? = null
    private var areCardsClickable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        sharedPref = activity!!.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)

        chronometer = view.findViewById(R.id.chronometer)
        startButton = view.findViewById(R.id.buttonStart)
        endButton = view.findViewById(R.id.buttonEnd)

        imageViews = listOf(
            view.findViewById(R.id.imageView1),
            view.findViewById(R.id.imageView2),
            view.findViewById(R.id.imageView3),
            view.findViewById(R.id.imageView4),
            view.findViewById(R.id.imageView5),
            view.findViewById(R.id.imageView6),
            view.findViewById(R.id.imageView7),
            view.findViewById(R.id.imageView8),
            view.findViewById(R.id.imageView9),
            view.findViewById(R.id.imageView10),
            view.findViewById(R.id.imageView11),
            view.findViewById(R.id.imageView12),
        )

        completedImages = BooleanArray(imageViews.size)

        for(imageView in imageViews) {
            imageView.setImageResource(R.drawable.hearth_cardback)
        }

        endButton.setOnClickListener {
            ConfirmExitDialog.build((activity as MainActivity), {
                (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.mainActivity, MainScreen()).commit()
            }).show()
        }

        startButton.setOnClickListener {
            startGame()
        }

        return view
    }

    private fun startGame() {
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
        areCardsClickable = true
        cardBacks = AVAILABLE_CARDS.shuffled()

        for((i, imageView) in imageViews.withIndex()) {
            imageView.setImageResource(cardBacks[i])
        }

        Handler(Looper.getMainLooper()).postDelayed({

            for((i,imageView) in imageViews.withIndex()) {

                imageView.setImageResource(R.drawable.hearth_cardback)

                imageView.setOnClickListener {
                    if(!areCardsClickable) {
                        return@setOnClickListener
                    }

                    if(flippedCardIndex == null) {

                        imageView.setImageResource(cardBacks[i])
                        completedImages[i] = !completedImages[i]
                        flippedCardIndex = i

                    } else {
                        if(flippedCardIndex == i) {
                            return@setOnClickListener
                        }

                        imageView.setImageResource(cardBacks[i])

                        if(cardBacks[flippedCardIndex!!] == cardBacks[i]) {

                            completedImages[i] = true
                            completedImages[flippedCardIndex!!] = true

                            flippedCardIndex = null

                            if(completedImages.all { it }) {
                                gameWon()
                            }
                        } else {
                            areCardsClickable = false

                            Handler(Looper.getMainLooper()).postDelayed({
                                imageView.setImageResource(R.drawable.hearth_cardback)
                                imageViews[flippedCardIndex!!].setImageResource(R.drawable.hearth_cardback)

                                flippedCardIndex = null
                                areCardsClickable = true
                            }, 500)
                        }
                    }
                }
            }

        }, 1000)
    }

    private fun onSuccess() {
        activity!!.runOnUiThread {
            Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "New record added to API",
                    Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun onError() {
        activity!!.runOnUiThread {
            Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "Error when connecting to API",
                    Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun gameWon() {

        chronometer.stop()
        val currDate = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().time)
        val username = sharedPref.getString("USERNAME", "default")
        val newPartida = Partida(username!!, chronometer.text.toString(), currDate)
        AddDialog.create(activity!!, chronometer.text.toString()).show()
        // guardar partida
        GestorPartidasDB.addPartida(newPartida)
        StatsApi.addNewRecord(newPartida, this::onSuccess, this::onError)
    }

    companion object {

        private val AVAILABLE_CARDS = listOf(
            R.drawable.dr_boom,
            R.drawable.dr_boom,
            R.drawable.hex,
            R.drawable.hex,
            R.drawable.dummy,
            R.drawable.dummy,
            R.drawable.lord,
            R.drawable.lord,
            R.drawable.mana,
            R.drawable.mana,
            R.drawable.molino_tormenta,
            R.drawable.molino_tormenta
        )

        @JvmStatic
        fun newInstance(user: String) : FragmentGame {
            val fragment = FragmentGame()
            val arguments = Bundle()
            arguments.putString("USERNAME", user)
            fragment.arguments = arguments
            return fragment
        }


    }

}