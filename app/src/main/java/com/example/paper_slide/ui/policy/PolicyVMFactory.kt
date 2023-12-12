package com.example.paper_slide.ui.policy

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PolicyVMFactory(val context: Context)  : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PolicyViewModel(context) as T
    }
}