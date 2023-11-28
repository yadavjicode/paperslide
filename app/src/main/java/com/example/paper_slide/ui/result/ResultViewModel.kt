package com.example.paper_slide.ui.result

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultViewModel (val context : Context) : ViewModel() {
    val TAG="resultSummery"
    fun validateSummery(
        originalText: String,
        progressVal: Int,
        progressBar: ProgressBar,
        regeneratedText: EditText
    ) {
        viewModelScope.launch {
            fetchSummery(originalText,progressVal,progressBar,regeneratedText)
        }

    }

    private suspend fun fetchSummery(
        summarizeData: String,
        progressVal: Int,
        progressBar: ProgressBar,
        regeneratedText: EditText
    ) {
        progressBar.visibility = View.VISIBLE

        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.getSummary(summarizeData, progressVal).execute()
            }

            if (response.isSuccessful) {
                progressBar.visibility = View.GONE
                regeneratedText.setText(response.body()?.summarized_text)
                Toast.makeText(context, response.body()?.summarized_text , Toast.LENGTH_SHORT).show()
                Log.d(TAG, "fetchSummery: ${response.body()?.summarized_text}")


            } else {
                progressBar.visibility = View.GONE

                Toast.makeText(context," ${response.errorBody()}", Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception){
            progressBar.visibility = View.GONE

            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "fetchSummery: ${e.message}")

        }
    }

    fun validateUpdateSummery(id: Int, updatedSummery: String) {

        viewModelScope.launch {
            updatedSummery(id,updatedSummery)
        }
    }

    private suspend fun updatedSummery(id: Int, updatedSummery: String) {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO){ apiClient.updateSummary(id,updatedSummery).execute() }

            if (response.isSuccessful){
                Log.d(TAG, " id=$id and updatedSummery: $updatedSummery")
                Toast.makeText(context, "${response.message()}", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(context, "${response.errorBody()}", Toast.LENGTH_LONG).show()
            }

        }catch (e :Exception){
            Toast.makeText(context, "${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}