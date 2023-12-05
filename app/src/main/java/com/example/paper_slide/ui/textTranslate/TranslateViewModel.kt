package com.example.paper_slide.ui.textTranslate

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.model.LanguageResponse
import com.example.paper_slide.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TranslateViewModel(val context: Context) : ViewModel() {
   // val languageArray = mutableListOf("Select Language")
    val TAG = "translatedLog"


    fun validateTranslation(
        originalText: String,
        langCode: String,
        translatedTV: EditText,
        progressBar: ProgressBar
    ) {
    viewModelScope.launch {
        //Toast.makeText(context, "$originalText and $langCode", Toast.LENGTH_LONG).show()
        fetchTranslated(originalText,langCode,translatedTV,progressBar)
    }

    }

    private suspend fun fetchTranslated(
        originalText: String,
        langCode: String,
        translatedTV: EditText,
        progressBar: ProgressBar
    ) {

        try {
            progressBar.visibility=View.VISIBLE
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.getTranslation(originalText, langCode).execute()
            }

            if (response.isSuccessful) {
                Log.d(TAG, "fetchTranslated: ${response.body()?.translated_text.toString()}")
                translatedTV.setText(response.body()?.translated_text.toString())
                progressBar.visibility=View.INVISIBLE
                Toast.makeText(context, "${response.body()?.translated_text}", Toast.LENGTH_LONG).show()
            }else{
                Log.d(TAG, "else fetchTranslated: ${response.errorBody()}")
                progressBar.visibility=View.INVISIBLE
                Toast.makeText(context, "${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        }catch (e : Exception){
            Log.d(TAG, "catch: ${e.message}")
            progressBar.visibility=View.INVISIBLE
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

/*
    suspend fun getLanguages() {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.getLanguage().execute()
            }
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d(TAG, "language: $responseBody")
                for (i in responseBody!!) {
                    languageArray.addAll(listOf(i.code))
                }
                Log.d(TAG, "language: $languageArray ${languageArray.size}")
            }
        } catch (e: Exception) {
            Log.d(TAG, "language: ${e.message}")
        }

    }
*/

    suspend fun getLanguages2(): List<LanguageResponse> {
        return try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.getLanguage().execute()
            }

            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.d(TAG, "language: ${e.message}")
            emptyList()
        }
    }

}


