package com.example.ivanov_p3.util.view

import android.content.Context
import android.widget.Toast

fun Context.toast(messageString: String) {

    val toast = Toast.makeText(this, messageString, Toast.LENGTH_SHORT)

    toast.show()
}