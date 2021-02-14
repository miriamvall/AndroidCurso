package com.curso.androidcurso.dialogs

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.TextView
import com.curso.androidcurso.R

class AddDialog {
    companion object {
        fun create(context: Context, tiempo: String): Dialog {
            val dialog = Dialog(context)

            dialog.setContentView(R.layout.dialog_finished_game)

            val okButton = dialog.findViewById<Button>(R.id.buttonDismiss)
            val message = dialog.findViewById<TextView>(R.id.textViewWin)

            val stringCompleted = context.getString(R.string.tiempo_juego)
            message.text = String.format(stringCompleted, tiempo)

            okButton.setOnClickListener {
                dialog.dismiss()
            }

            return dialog
        }
    }
}