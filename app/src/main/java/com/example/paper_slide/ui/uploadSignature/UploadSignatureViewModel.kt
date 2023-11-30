package com.example.paper_slide.ui.uploadSignature

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadSignatureViewModel(val context : Context) : ViewModel() {
    private val TAG = "signatureLog"
    fun validateSignature(imageUri: MultipartBody.Part) {
        viewModelScope.launch {
            uploadSignature(imageUri)
        }
    }

    private suspend fun uploadSignature(imageUri: MultipartBody.Part) {
        try {
            // Convert URI to File
           // val imageFile = uriToFile(imageUri)

            // Create a request body with the image file
           // val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
          //  val body = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)


            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.postSignature(imageUri).execute()
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

    // Function to convert URI to File
/*
    private fun uriToFile(uri: MultipartBody.Part): File {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val filePath = cursor?.getString(columnIndex ?: 0) ?: ""
        cursor?.close()
        return File(filePath)
    }
*/
}