package com.curso.androidcurso.dialogs

import android.app.AlertDialog
import android.content.Context

object ConfirmExitDialog {

    fun build(context: Context, confirmExitCallback: () -> Unit): AlertDialog {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Are you sure you want to exit?")
        builder.setMessage("Changes cannot be undone")

        builder.setPositiveButton("Yes") { _, _ ->
            confirmExitCallback()
        }

        builder.setNegativeButton("No"){ _, _ -> }

        return builder.create()
    }

}