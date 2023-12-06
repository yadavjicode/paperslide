package com.example.paper_slide.ui.texteditor

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paper_slide.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TextEditorViewModel(val context: Context) : ViewModel() {
    private val TAG = "noteLogs"
    fun validateNote(titleNote: String, editorText: String) {
        viewModelScope.launch { 
            postNote(titleNote,editorText)
        }
    }

    private suspend fun postNote(titleNote: String, editorText: String) {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO){
                apiClient.postNote(titleNote,editorText).execute()
            }
            
            if (response.isSuccessful){
                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                Log.d(TAG, "postNote: ${response.body()}")
            }else{
                Toast.makeText(context," ${response.errorBody()}", Toast.LENGTH_SHORT).show()

            }
            
        }catch (e:Exception){
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "postNote: ${e.message}")
        }
    }
}