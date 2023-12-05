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
import com.example.paper_slide.model.LanguageResponse
import kotlinx.coroutines.launch

class TextTranslateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTextScannerTranslateBinding
    private lateinit var translateViewModel: TranslateViewModel
    private val context = this@TextTranslateActivity
    private lateinit var summaryData : String
    private lateinit var originalText : String
    val TAG ="translateActivityLog"
    //private var selectedlanguage = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,
            R.layout.activity_text_scanner_translate)

        translateViewModel = ViewModelProvider(context,
            TranslateVMFactory(applicationContext)
        )[TranslateViewModel::class.java]

        summaryData = intent.getStringExtra("summaryData").toString()
        initViews()
        fetchLanguages()
       // initLang()
    }

/*    private fun initLang() {
    lifecycleScope.launch {
        translateViewModel.getLanguages()
       // getSpinner()
    }
    }

    private fun getSpinner() {
        val arrayAdapter =ArrayAdapter(this, android.R.layout.simple_spinner_item,
            translateViewModel.languageArray
        )

        binding.languageSpinner.adapter = arrayAdapter

        binding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedlanguage = parent?.selectedItem.toString()
                    if (selectedlanguage == "Select Language") {
                        Toast.makeText(context, "Please Select the Language", Toast.LENGTH_SHORT)
                            .show()
                    }else if(selectedlanguage == "hin_Deva") {
                        binding.progressBar.visibility= View.VISIBLE
                        originalText = binding.originalText.text.toString()
                        val translatedTV = binding.translatedText

                        if (originalText != null && selectedlanguage != null) {
                            lifecycleScope.launch {

                                translateViewModel.validateTranslation(originalText, selectedlanguage,translatedTV)
                                binding.progressBar.visibility= View.INVISIBLE

                            }
                        } else{
                            Toast.makeText(context, "originalText or langCode null", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(context, selectedlanguage, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    selectedlanguage = p0?.selectedItem.toString()
                }
            }

    }*/

    private fun initViews() {
        binding.originalText.setText(summaryData)
    }
    private fun fetchLanguages() {
        lifecycleScope.launch {
            val languages = getLanguages()

            // Add a default language to the list if needed
            val languageList = mutableListOf("Select Language")
            languageList.addAll(languages.map { it.name })

            // Create an ArrayAdapter using the language list and the default spinner layout
            val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                languageList
            )

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Apply the adapter to the spinner
            binding.languageSpinner.adapter = adapter

            // Set a listener to handle item selection
            binding.languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    if (position > 0) {
                        // Display the language code in a toast
                        val selectedLanguage = languages[position - 1]
                       /* Toast.makeText(
                            context,
                            "Selected Language Code: ${selectedLanguage.code}",
                            Toast.LENGTH_SHORT
                        ).show()*/
                        if (selectedLanguage.name == "Select Language") {
                            Toast.makeText(context, "Please Select the Language", Toast.LENGTH_SHORT)
                                .show()
                        }else if(selectedLanguage.code == "hin_Deva") {
                            originalText = binding.originalText.text.toString()
                            val translatedTV = binding.translatedText

                            if (originalText != null && selectedLanguage.code != null) {
                                lifecycleScope.launch {
                                    translateViewModel.validateTranslation(originalText, selectedLanguage.code,translatedTV,binding.progressBar)
                                }
                            } else{
                                Toast.makeText(context, "originalText or langCode null", Toast.LENGTH_SHORT).show()
                            }
                        }else {
                            Toast.makeText(context, selectedLanguage.code, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing when nothing is selected
                }
            }
        }
    }

    private suspend fun getLanguages(): List<LanguageResponse> {
        return try {
            translateViewModel.getLanguages2()
        } catch (e: Exception) {
            Log.d(TAG, "language: ${e.message}")
            emptyList()
        }
    }

}