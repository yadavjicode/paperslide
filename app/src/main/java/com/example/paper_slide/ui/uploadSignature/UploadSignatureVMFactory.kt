package com.example.paper_slide.ui.uploadSignature

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UploadSignatureVMFactory(val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UploadSignatureViewModel(context) as T
    }
}