package com.example.paper_slide.ui.otp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import com.example.paper_slide.ui.resetpassword.ResetPassword
import com.example.paper_slide.util.SharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OTPViewModel(val context : Context) : ViewModel() {
    val TAG  ="OTPLogs"
    private val sharedPref = SharedPref(context)
    fun validateForgetPassword(email: String, otp : Int) {
        viewModelScope.launch {
            fetchForgotData(email, otp)
            Log.d(TAG, "validateForgetPassword: $otp")
        }
    }

    private suspend fun fetchForgotData(email: String , getOTP : Int) {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.getOTP(email, getOTP).execute()
            }

            if (response.isSuccessful) {

                Log.d(TAG, "fetchData: success ${response.body()}")
                Toast.makeText(
                    context,
                    "Success :${response.body()?.message.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
                launchActivity(ResetPassword::class.java, email)
            } else {
                Toast.makeText(
                    context,
                    "Error: ${response.body()?.message.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Log.d(TAG, "Validate OTP : ${e.message}")
        }
    }

     fun launchActivity(newActivity : Class<*>, email: String ) {
        val intent = Intent(context, newActivity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("email" , email)
        context.startActivity(intent)
    }

     fun jobSchedule(countdownTextView : TextView, activity: Activity) {
           CoroutineScope(Dispatchers.Main).launch {
            var remainingTimeSeconds = 180
            while (remainingTimeSeconds > 0) {
                val minutes = remainingTimeSeconds / 60
                val seconds = remainingTimeSeconds % 60
                countdownTextView.text = String.format("%02d:%02d", minutes, seconds)
                delay(1000)
                remainingTimeSeconds--
            }
            activity.finish()
        }
    }
}