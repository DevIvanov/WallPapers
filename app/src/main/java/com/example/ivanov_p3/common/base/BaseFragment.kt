package com.example.ivanov_p3.common.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.ivanov_l22_t1.util.view.AlertDialogUtil
import com.example.ivanov_l22_t1.util.view.toast

abstract class BaseFragment (@LayoutRes layoutId: Int) : Fragment(layoutId) {

    fun toast(message: String) {
        requireContext().toast(message)
    }

    fun showAlertDialog(title: String, message: String): AlertDialogUtil {
        return AlertDialogUtil(title, message)
    }
}