package com.example.ivanov_p3.util.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

fun Context.showAlertDialog(title: String, message: String) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder.setTitle(title)
        .setMessage(message)
        .setPositiveButton(
            "OK"
        ) { dialog, _ ->
            dialog.cancel()
        }
    builder.create().show()
}