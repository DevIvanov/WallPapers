package com.example.ivanov_p3.util.view


import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.domain.model.History
import com.example.ivanov_p3.R


class MyDialogFragment(
    private val listener: OnClickListenerDialog
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val itemsToSelect = arrayOf(resources.getString(R.string.delete_one),
                resources.getString(R.string.delete_all))

            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.title_dialog)
                .setItems(itemsToSelect
                ) { _, which ->
                    if (which == 0) {
                        listener.onDialogClick(0)
                    }else {
                        listener.onDialogClick(1)
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface OnClickListenerDialog {
        fun onDialogClick(index: Int) {}
    }
}

