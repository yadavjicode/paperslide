package com.example.paper_slide.ui.resetpassword

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import com.example.paper_slide.ui.signin.Signin
import com.example.paper_slide.util.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResetPasswordViewModel(val context : Context) : ViewModel() {
    val TAG = "ResetPasswordLogs"
    private val sharedPref = SharedPref(context)
    suspend fun validateResetPass( password: String, progressBar: ProgressBar, activity: Activity){
        viewModelScope.launch {
            fetchData( password, progressBar, activity)
        }
    }

    fun startNewActivity(newActivity : Class<*>) {
        val intent = Intent(context, newActivity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }



    private suspend fun fetchData( password: String, progressBar: ProgressBar, activity: Activity) {
        progressBar.visibility = View.VISIBLE
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {apiClient.getResetPassword(password).execute()}

            if (response.isSuccessful) {
                progressBar.visibility = View.GONE

                sharedPref.accessToken = response.body()?.access_token.toString()
                startNewActivity(Signin::class.java)
                activity.finish()
                Log.d(TAG, "fetchData: ${response.body()}")
                Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                progressBar.visibility = View.GONE
                Toast.makeText(context, response.body()?.access_token.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            progressBar.visibility = View.GONE
            Log.d(TAG, "validateResetPass: ${e.message}")
        }
    }


}