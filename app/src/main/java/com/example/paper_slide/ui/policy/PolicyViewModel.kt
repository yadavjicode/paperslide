package com.example.paper_slide.ui.policy

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

class PolicyViewModel(val context: Context) : ViewModel() {
    private val TAG = "policyLog"
    fun validatePolicy(policyTV: TextView) { 
        viewModelScope.launch { 
            fetchPolicy(policyTV)
        }
    }

    private suspend fun fetchPolicy(policyTV: TextView) {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO){ apiClient.getPrivacy().execute() }
            
            if (response.isSuccessful){
                policyTV.text= response.body()!!.text
                Log.d(TAG, "fetchPolicy: ${response.body()}")
            }else{
                Log.d(TAG, "fetchPolicy: ${response.errorBody()}")
                Toast.makeText(context, "${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
            
        }catch (e:Exception){
            Log.d(TAG, "fetchPolicy: ${e.message}")
            Toast.makeText(context, "${e.message}}", Toast.LENGTH_SHORT).show()

        }

    }
}