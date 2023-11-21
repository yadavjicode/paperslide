package com.example.paper_slide.ui.summarize

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SummarizeViewModel (val context : Context) : ViewModel() {
    private val TAG = "summeryLog"
    fun validateSummery(summarizeData: String, progressVal: Int, progressBar: ProgressBar) {
        viewModelScope.launch {
            fetchSummery(summarizeData,progressVal,progressBar)
        }
    }

    private suspend fun fetchSummery(
        summarizeData: String,
        progressVal: Int,
        progressBar: ProgressBar
    ) {
        progressBar.visibility = View.VISIBLE

        try {


            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.getSummary(summarizeData, progressVal).execute()
            }

            if (response.isSuccessful) {
                progressBar.visibility = View.GONE

                Toast.makeText(context, response.body()?.summarized_text , Toast.LENGTH_SHORT).show()
                Log.d(TAG, "fetchSummery: ${response.body()?.summarized_text}")

            } else {
                Toast.makeText(context," ${response.errorBody()}", Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception){
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "fetchSummery: ${e.message}")
        }
    }
    }
