package com.example.paper_slide.ui.textTranslate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityTextScannerTranslateBinding
import kotlinx.coroutines.launch

class TextTranslateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTextScannerTranslateBinding
    private lateinit var translateViewModel: TranslateViewModel
    private val context = this@TextTranslateActivity
    private lateinit var summaryData : String
    private lateinit var originalText : String
    private lateinit var langCode : String
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
        binding.summarizeBtn.setOnClickListener {
            originalText = binding.originalText.text.toString()
            langCode = binding.languageCode.text.toString()
            val translatedTV = binding.translatedText

            if (originalText != null && langCode != null) {
                lifecycleScope.launch {
                    translateViewModel.validateTranslation(originalText, langCode,translatedTV)
                }
            } else{
                Toast.makeText(context, "originalText or langCode null", Toast.LENGTH_SHORT).show()
        }
        }

    }


}