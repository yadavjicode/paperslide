package com.example.paper_slide.ui.summarize

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SummarizeVMFactory (val context : Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SummarizeViewModel(context) as T
    }
}