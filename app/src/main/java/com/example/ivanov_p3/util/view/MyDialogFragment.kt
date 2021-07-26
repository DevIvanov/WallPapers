package com.example.ivanov_p3.util.view


import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.domain.model.History
import com.example.ivanov_p3.R
import com.example.ivanov_p3.ui.HistoryViewModel


class MyDialogFragment(var itemsToSelect: Array<String>,
                       val mHistoryViewModel: HistoryViewModel,
                       val currentItem: History
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.title_dialog)
                .setItems(itemsToSelect
                ) { dialog, which ->
                    if (which == 0) {
                        mHistoryViewModel.deleteData(currentItem)
                        Toast.makeText(
                            activity,
                            itemsToSelect[which],
                            Toast.LENGTH_SHORT
                        ).show()
                    }else {
                        mHistoryViewModel.deleteAllData()
                        Toast.makeText(
                            activity,
                            itemsToSelect[which],
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}