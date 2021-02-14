package com.curso.androidcurso.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.curso.androidcurso.dialogs.ConfirmExitDialog
import com.curso.androidcurso.MainActivity
import com.curso.androidcurso.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)

        val buttonSum: Button = view.findViewById(R.id.buttonSum)
        val buttonMult: Button = view.findViewById(R.id.buttonMult)
        val result: TextView = view.findViewById((R.id.result))
        val number1: TextView = view.findViewById(R.id.numero1)
        val number2: TextView = view.findViewById(R.id.numero2)

        buttonSum.setOnClickListener{

            if(number1.text.toString().isNotEmpty() && number2.text.toString().isNotEmpty()) {
                result.text = (number1.text.toString().toFloat() + number2.text.toString().toFloat()).toString()
            }
        }

        buttonMult.setOnClickListener{

            if(number1.text.toString().isNotEmpty() && number2.text.toString().isNotEmpty()) {
                result.text = (number1.text.toString().toFloat() * number2.text.toString().toFloat()).toString()
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}