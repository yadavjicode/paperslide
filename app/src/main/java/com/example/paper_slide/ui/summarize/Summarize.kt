package com.example.paper_slide.ui.summarize

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivitySummarizeBinding
import kotlinx.coroutines.launch

class Summarize : AppCompatActivity() {
    private  var progressVal : Int = 10
    private lateinit var summarizeViewModel: SummarizeViewModel
    private  val context = this@Summarize
    private lateinit var binding: ActivitySummarizeBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var copyText : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            context,
            R.layout.activity_summarize
        )
        summarizeViewModel = ViewModelProvider(
            context,
            SummarizeVMFactory(applicationContext)
        )[SummarizeViewModel::class.java]
        initViews()
    }
    private fun initViews() {
        seekBar()
        binding.summarizeBtn.setOnClickListener {
            validateViews()
        }
        progressBar=binding.progressBar
        val ocrText=intent.getStringExtra("summaryData").toString()
        binding.summarizeText.setText(ocrText)
        binding.copySummarizeIV.setOnClickListener {
            copyText = binding.summarizeText.text.toString()
            Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
            copyToClipboard(copyText)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    private fun validateViews() {
        val summarizeData = binding.summarizeText.text
        //copyText = summarizeData.toString()
    if(summarizeData != null) {
      //  Toast.makeText(context,"$summarizeData , $progressVal"  , Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
           summarizeViewModel.validateSummery(summarizeData.toString(),progressVal,binding.progressBar)
           // Toast.makeText(context, "$progressVal", Toast.LENGTH_SHORT).show()
            Log.d("SummeryData", "validateViews: $summarizeData")
        }
    }else{
        Toast.makeText(context, "Provide Text", Toast.LENGTH_SHORT).show()
     }
    }
    private fun copyToClipboard(summarizeData: String) {
        val clipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // Create a new ClipData with the text to copy
        val clipData = ClipData.newPlainText("text", summarizeData)
        // Set the created ClipData to the clipboard
        clipboardManager.setPrimaryClip(clipData)
    }
    private fun seekBar() {
        binding.seekBar.max = 10
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.seekBar.min = 1
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progressVal = progress*10
                binding.seekbarnum.text = "${progress * 10} /100"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(
                    applicationContext,
                    "Progress: ${seekBar?.progress}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}