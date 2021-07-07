package com.example.ivanov_p3.common.base

import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.ivanov_l22_t1.util.view.AlertDialogUtil
import com.example.ivanov_l22_t1.util.view.toast
import com.example.ivanov_p3.util.view.hideKeyboard

abstract class BaseFragment (@LayoutRes layoutId: Int) : Fragment(layoutId) {

    fun toast(message: String) {
        requireContext().toast(message)
    }

    fun showAlertDialog(title: String, message: String): AlertDialogUtil {
        return AlertDialogUtil(title, message)
    }

    fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    fun backPressed () {
        var backPressedTime:Long = 0
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

            val backToast: Toast = Toast.makeText(activity, "Press back again to exit", Toast.LENGTH_SHORT)
            if (backPressedTime + 1000 > System.currentTimeMillis()) {
                backToast.cancel()
                activity?.finish()
            } else {
                backToast.show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }

    fun hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }
}