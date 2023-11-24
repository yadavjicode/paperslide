package com.example.paper_slide.ui.textTranslate

import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TranslateViewModel(val context: Context) : ViewModel() {
    val TAG = "translatedLog"

    fun validateTranslation(originalText: String, langCode: String, translatedTV: EditText) {
    viewModelScope.launch {
        //Toast.makeText(context, "$originalText and $langCode", Toast.LENGTH_LONG).show()
        fetchTranslated(originalText,langCode,translatedTV)
    }

    }

    private suspend fun fetchTranslated(
        originalText: String,
        langCode: String,
        translatedTV: EditText
    ) {

        try {


            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.getTranslation(originalText, langCode).execute()
            }

            if (response.isSuccessful) {
                Log.d(TAG, "fetchTranslated: ${response.body()?.translated_text.toString()}")
                translatedTV.setText(response.body()?.translated_text.toString())
                Toast.makeText(context, "${response.body()?.translated_text}", Toast.LENGTH_LONG).show()
            }else{
                Log.d(TAG, "else fetchTranslated: ${response.errorBody()}")

                Toast.makeText(context, "${response.errorBody()}", Toast.LENGTH_SHORT).show()
            }
        }catch (e : Exception){
            Log.d(TAG, "catch: ${e.message}")

            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}