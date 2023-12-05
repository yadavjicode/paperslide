package com.example.paper_slide.ui.uploadSignature

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class UploadSignatureViewModel(val context : Context) : ViewModel() {
    private val TAG = "signatureLog"
    fun validateSignature(imagePart: MultipartBody.Part) {
        viewModelScope.launch {
            uploadSignature(imagePart)
        }
    }
    private suspend fun uploadSignature(imagePart: MultipartBody.Part) {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.postSignature(imagePart).execute()
            }
            if (response.isSuccessful)
            {
                Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "uploadSignature: ${response.body()?.message}")
            }else{
                Toast.makeText(context, "${response.errorBody()}", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "else: ${response.errorBody()}")
            }
        }catch (e : Exception){
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "catch: ${e.message}")
        }
    }
}