package com.example.paper_slide.ui.signin

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import com.example.paper_slide.ui.home.Home
import com.example.paper_slide.util.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel (val context: Context):ViewModel(){

    val TAG="SignInLogs"
    private val sharedPref = SharedPref(context)



    suspend fun validateSignIn(
        username :String,
        password :String?,
        progressBar: ProgressBar
    ){
        viewModelScope.launch {
            fetchData(username, password!!, progressBar)
        }
    }



    fun startNewActivity(newActivity : Class<*>) {
        val intent = Intent(context, newActivity)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        context.startActivity(intent)
    }



    private suspend fun fetchData(
        username: String,
        password: String,
        progressBar: ProgressBar,
    ) {
        progressBar.visibility = View.VISIBLE
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {apiClient.getSignIn(username, password).execute()}
            if (response.isSuccessful) {
                progressBar.visibility = View.GONE
                sharedPref.refreshToken = response.body()?.access_token.toString()
                sharedPref.login = "Yes"
                startNewActivity(Home::class.java)
                Log.d(TAG, "fetchData: ${response.body()} ${APIInterface.APIClient(context).apiInstance}")
                Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            } else {
                progressBar.visibility = View.GONE
                Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
                Log.d(TAG, "validateSignIn Response not Successful: ${ response.body()}")
            }
        } catch (e: Exception) {
            progressBar.visibility = View.GONE
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "validateSignIn: ${e.message}")
        }
    }



}