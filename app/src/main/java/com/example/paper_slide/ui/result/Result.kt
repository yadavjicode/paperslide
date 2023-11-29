package com.example.paper_slide.ui.result

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
import com.example.paper_slide.databinding.ActivityResultBinding
import kotlinx.coroutines.launch

class Result : AppCompatActivity() {
    private lateinit var resultViewModel: ResultViewModel
    private lateinit var binding : ActivityResultBinding
    private  val context = this@Result
    private  var progressVal : Int = 10
    private lateinit var progressBar: ProgressBar
    private var id :Int  =0
    private val TAG = "resultLog"
    private lateinit var   updatedSummery : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,R.layout.activity_result)

        resultViewModel = ViewModelProvider(context,
            ResultVMFactory(applicationContext)
        )[ResultViewModel::class.java]
        seekbar()
        initViews()
    }
    private fun initViews() {
        binding.resultET.setText(intent.getStringExtra("summarizedText"))
        binding.regenerateTV.setOnClickListener {
            validateViews()
        }
        progressBar=binding.progressBar

        id = intent.getIntExtra("id",82)

        binding.saveBtn.setOnClickListener {
            validateResultET()
        }
    }
    private fun validateResultET() {
        val resultET = binding.resultET
        updatedSummery  = resultET.text.toString()
        if(id !=0 && updatedSummery.isNotEmpty()){
            lifecycleScope.launch {
                 resultViewModel.validateUpdateSummery(id,updatedSummery)
              //  Toast.makeText(context, updatedSummery, Toast.LENGTH_SHORT).show()
                Log.d(TAG, "initViews: $updatedSummery")
            }
        }else{
            Toast.makeText(context, "Check id and updated summery ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateViews() {
        val originalText = intent.getStringExtra("originalText")
        val regeneratedText = binding.resultET
        if(originalText != null) {
            lifecycleScope.launch {
                 resultViewModel.validateSummery(originalText,progressVal,binding.progressBar,regeneratedText)
                Toast.makeText(context, "$progressVal", Toast.LENGTH_SHORT).show()
                Log.d("originalText", "validateViews: ${originalText}")
            }
        }else{
            Toast.makeText(context, "Provide Text", Toast.LENGTH_SHORT).show()
        }
    }
    private fun seekbar() {
        binding.resultseekBar.max =10
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.resultseekBar.min = 1
        }
        binding.resultseekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progressVal = progress*10

                binding.resultValue.text ="$progressVal /100"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext, "Progress: ${seekBar?.progress}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}