package com.example.paper_slide.ui.textTranslate

import android.R
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TranslateViewModel(val context: Context) : ViewModel() {
    val languageArray = mutableListOf<String>()
    val TAG = "translatedLog"
    var languageN : List<String> = emptyList()


    fun validateLanguages(languageNames: List<String>, languageSpinner: Spinner, langCode: List<String>) {
        viewModelScope.launch {
            //getLanguages(languageNames,languageSpinner,langCode)
        }
    }
    fun validateTranslation(originalText: String, langCode: List<String>, translatedTV: EditText) {
    viewModelScope.launch {
        //Toast.makeText(context, "$originalText and $langCode", Toast.LENGTH_LONG).show()
        fetchTranslated(originalText,langCode,translatedTV)
    }

    }

    private suspend fun fetchTranslated(
        originalText: String,
        langCode: List<String>,
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

    /*   private suspend fun getLanguages(
           languageNames: List<String>,
           languageSpinner: Spinner,
           langCode: List<String>
       ) {

           try {


               val apiClient = APIInterface.APIClient(context).apiInstance
               val response = withContext(Dispatchers.IO) {
                   apiClient.getLanguage().execute()
               }

               if (response.isSuccessful) {
                   val languageResponse = response.body()
                   languageResponse?.let {
                       val languages = it.

                       // Extract language names
                      languageN = languages.map { language -> language.name}

                       // Set up the ArrayAdapter for the Spinner
                       val spinnerAdapter =
                           ArrayAdapter(context, R.layout.simple_spinner_item, languageN)
                       spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                       languageSpinner.adapter = spinnerAdapter
                   }
               } else {

                   Toast.makeText(context, "${response.errorBody()}", Toast.LENGTH_SHORT).show()

               }
           }catch (e:Exception){
               Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
           }

   }*/


}