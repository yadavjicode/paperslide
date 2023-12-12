package com.example.paper_slide.ui.tc

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TCViewModel(val context: Context) : ViewModel() {
    private val TAG ="tcLog"
    fun validateTC(tcTv: TextView) {
        viewModelScope.launch {
            getTC(tcTv)
        }
    }

    private suspend fun getTC(tcTv: TextView) {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance

            val response = withContext(Dispatchers.IO){ apiClient.getTC().execute() }

            if (response.isSuccessful){
                tcTv.text = response.body()!!.text
            }else{
                Toast.makeText(context, "${response.message()}", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "getTC: ${response.errorBody()}")
            }
        }catch (e : Exception){
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "getTC: ${e.message}")
        }
    }

}