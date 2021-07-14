package com.example.ivanov_p3.util.view


import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object PreferenceHelper {

    const val CUSTOM_PREF_NAME = "User_data"
    private const val USER_STATE = "USER_STATE"
    private const val USER_QUERY= "USER_QUERY"
    private const val USER_COLUMNS= "USER_COLUMNS"

    fun defaultPreference(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.state
        get() = getBoolean(USER_STATE, false)
        set(value) {
            editMe {
                it.putBoolean(USER_STATE, value)
            }
        }

    var SharedPreferences.query
        get() = getString(USER_QUERY, "")
        set(value) {
            editMe {
                it.putString(USER_QUERY, value)
            }
        }

    var SharedPreferences.columns
        get() = getBoolean(USER_COLUMNS, false)
        set(value) {
            editMe {
                it.putBoolean(USER_COLUMNS, value)
            }
        }
}
