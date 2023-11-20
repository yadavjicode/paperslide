package com.example.paperslide.ui.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paperslide.network.APIInterface
import com.example.paperslide.ui.signin.Signin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(val context: Context) :ViewModel() {

val TAG ="SignUpLogs"


    suspend fun validateSignUp(
        name: String,
        email: String,
        phone: String,
        password: String,
        activity: Activity
    ){
        viewModelScope.launch {
            fetchData(name, email, phone, password, activity)
        }


    }

    private suspend fun fetchData(name: String, email: String, phone: String, password: String, activity: Activity)
    {
        try{
        val apiClient = APIInterface.APIClient(context).apiInstance

        val response = withContext(Dispatchers.IO) {
            apiClient.getSignUp(name, email, phone, password).execute()
        }
            Log.d(TAG, "response ${response}")
        if (response.isSuccessful) {
            Log.d(TAG, "fetchData: ${response.body()}")
            Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
            startNewActivity(Signin::class.java)
            activity.finish()
      //  } else if (response.body()?.statusCode == 409) {
        //    Log.d(TAG, "fetchData: ${response.body()?.message.toString()}")
        } else  {
            Log.d(TAG, "fetchData: ${response.body()?.message.toString()}")
            Toast.makeText(context, "${response.toString()}", Toast.LENGTH_SHORT).show()
        }
    }catch (e: Exception) {
        Toast.makeText(context, "Exception ${e.message}", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "validateSignUp: ${e.message}")
             }

    }


   fun startNewActivity(newActivity: Class<*>) {
      val intent = Intent(context, newActivity)
     intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      context.startActivity(intent)
        }
}