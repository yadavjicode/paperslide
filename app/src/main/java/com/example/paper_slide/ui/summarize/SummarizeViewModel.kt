package com.example.paper_slide.ui.summarize

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import com.example.paper_slide.ui.result.Result
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
                val original_text = response.body()?.original_text

                Toast.makeText(context, response.body()?.summarized_text , Toast.LENGTH_SHORT).show()
                Log.d(TAG, "fetchSummery: ${response.body()?.summarized_text}")
                        startNewActivity(response.body()?.summarized_text,
                            response.body()?.original_text ,
                            response.body()?.id,
                            Result::class.java)

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

    private fun startNewActivity(
        summarizedText: String?,
        originalText: String?,
        id: Int?,
        newActivity: Class<*>
    ) {
        val intent = Intent(context,newActivity)
        intent.putExtra("summarizedText", summarizedText)
        intent.putExtra("originalText", originalText)
        intent.putExtra("id", id)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}
