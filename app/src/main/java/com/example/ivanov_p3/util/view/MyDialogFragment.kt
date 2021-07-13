package com.example.ivanov_p3.util.view


import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class MyDialogFragment(var itemsToSelect: Array<String>) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            builder
                .setItems(itemsToSelect
                ) { dialog, which ->
                    Toast.makeText(activity,
                        itemsToSelect[which],
                        Toast.LENGTH_SHORT).show()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}