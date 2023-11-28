package com.example.paper_slide.ui.textTranslate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
    private lateinit var langCode : List<String>
    private val dummyData = listOf("English", "Spanish", "French", "German", "Italian")
    val TAG ="translateActivityLog"
    private lateinit var languageNames: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,
            R.layout.activity_text_scanner_translate)

        translateViewModel = ViewModelProvider(context,
            TranslateVMFactory(applicationContext)
        )[TranslateViewModel::class.java]

        summaryData = intent.getStringExtra("summaryData").toString()


        initViews()
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, dummyData)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.languageSpinner.adapter = spinnerAdapter
        initLang()
    }

    private fun initLang() {
    lifecycleScope.launch {
        translateViewModel.validateLanguages(languageNames,binding.languageSpinner,langCode)

    }
        binding.languageSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = dummyData[position]
                showToast("Selected Language: $selectedLanguage")
                Log.d(TAG, "onItemSelected: $selectedLanguage")
                // Handle item selection if needed
                //val selectedLanguage = languageNames[position]
                // Do something with the selected language name
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = dummyData[position]
                Toast.makeText(context, selectedLanguage, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun initViews() {
        binding.originalText.setText(summaryData)
        binding.summarizeBtn.setOnClickListener {
            originalText = binding.originalText.text.toString()
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}