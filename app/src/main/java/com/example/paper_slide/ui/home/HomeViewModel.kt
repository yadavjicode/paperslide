package com.example.paper_slide.ui.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel (val context : Context) : ViewModel() {
    private val TAG="homelog"

    fun validateLogout(){
        viewModelScope.launch {
            logoutUser()
        }
    }

    suspend fun logoutUser() {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO){
                apiClient.getLogout().execute()
            }
            if (response.isSuccessful) {

                Toast.makeText(context, "${response.message()}", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }

        }catch (e : Exception){

            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()        }
    }


}