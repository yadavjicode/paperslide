package com.example.paper_slide.ui.textTranslate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityTextScannerTranslateBinding
import dagger.hilt.android.qualifiers.ApplicationContext

class TextTranslateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTextScannerTranslateBinding
    private lateinit var translateViewModel: TranslateViewModel
    private val context = this@TextTranslateActivity
    private lateinit var summaryData : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,
            R.layout.activity_text_scanner_translate)

        translateViewModel = ViewModelProvider(context,
            TranslateVMFactory(applicationContext)
        )[TranslateViewModel::class.java]

        summaryData = intent.getStringExtra("summaryData").toString()

        initViews()
    }

    private fun initViews() {
        binding.originalText.setText(summaryData)
    }


}