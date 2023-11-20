package com.example.paperslide.util

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context : Context) {

    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    // Shared pref mode
    var PRIVATE_MODE = 0

    init {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    var login: String?
        get() = pref.getString("login", "no")
        set(isLoggedIn) {
            editor.putString("login", isLoggedIn)
            editor.commit()
        }

    var userName: String?
        get() = pref.getString("name", "")
        set(name) {
            editor.putString("name", name)
            editor.commit()
        }
    var userPhoto: String?
        get() = pref.getString("photo", "")
        set(photoUrl) {
            editor.putString("photo", photoUrl)
            editor.commit()
        }

        var refreshToken: String?
        get() = pref.getString("token", "")
        set(token) {
            editor.putString("token", token)
            editor.commit()
        }

       var accessToken: String?
        get() = pref.getString("token", "")
        set(token) {
            editor.putString("token", token)
            editor.commit()
        }

    companion object {
        // LogCat tag
        private val TAG = SharedPref::class.java.simpleName

        // Shared preferences file name
        private const val PREF_NAME = "AndroidHiveLogin"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }
}