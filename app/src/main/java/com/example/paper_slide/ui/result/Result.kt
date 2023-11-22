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
        binding.resulttext.setText(intent.getStringExtra("summarizedText"))
        binding.regenerateTV.setOnClickListener {
            validateViews()
        }
        progressBar=binding.progressBar

    }

    private fun validateViews() {
        val originalText = intent.getStringExtra("originalText")
        val regeneratedText = binding.resulttext


        if(originalText != null) {
            lifecycleScope.launch {
                resultViewModel.validateSummery(originalText,progressVal,binding.progressBar,regeneratedText)
                // Toast.makeText(context, "$progressVal", Toast.LENGTH_SHORT).show()
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

                binding.resultValue.text ="${progress * 10} /100"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                Toast.makeText(applicationContext, "Progress: ${seekBar?.progress}", Toast.LENGTH_SHORT).show()
            }


        })    }
}