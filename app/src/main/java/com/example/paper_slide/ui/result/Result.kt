package com.example.paper_slide.ui.result

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.example.paper_slide.R

class Result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val seekBar: SeekBar =findViewById(R.id.resultseekBar)
        val value: TextView =findViewById(R.id.result_value)


        seekBar.max =10
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.min = 1
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                value.text ="${progress * 10} /100"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                Toast.makeText(applicationContext, "Progress: ${seekBar?.progress}", Toast.LENGTH_SHORT).show()
            }


        })
    }
}