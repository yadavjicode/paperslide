package com.example.paper_slide.ui.result

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityResultBinding
import com.example.paper_slide.ui.summarize.SummarizeVMFactory

class Result : AppCompatActivity() {
    private lateinit var resultViewModel: ResultViewModel
    private lateinit var binding : ActivityResultBinding
    private  val context = this@Result


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
    }

    private fun seekbar() {
        binding.resultseekBar.max =10
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.resultseekBar.min = 1
        }
        binding.resultseekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.resultValue.text ="${progress * 10} /100"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                Toast.makeText(applicationContext, "Progress: ${seekBar?.progress}", Toast.LENGTH_SHORT).show()
            }


        })    }
}