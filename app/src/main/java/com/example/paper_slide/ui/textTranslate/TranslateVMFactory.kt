package com.example.paper_slide.ui.textTranslate

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TranslateVMFactory (val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TranslateViewModel(context) as T
    }
}