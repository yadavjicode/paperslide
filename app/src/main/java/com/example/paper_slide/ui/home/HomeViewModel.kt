package com.example.paper_slide.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import com.example.paper_slide.ui.signin.Login
import com.example.paper_slide.ui.signin.Signin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel (val context : Context) : ViewModel() {
    private val TAG="homelog"

    fun validateLogout(home: Home) {
        viewModelScope.launch {
            logoutUser(home)
        }
    }

    suspend fun logoutUser(home: Home) {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO){
                apiClient.getLogout().execute()
            }
            if (response.isSuccessful) {
                Log.d(TAG, "logoutUser: ${response.message()}")
                Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                startNewActivity(Signin::class.java)
                home.finish()

            }else{
                Toast.makeText(context, "${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }

        }catch (e : Exception){

            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()        }
    }

    fun startNewActivity(newActivity: Class<*>) {
        val intent = Intent(context, newActivity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}