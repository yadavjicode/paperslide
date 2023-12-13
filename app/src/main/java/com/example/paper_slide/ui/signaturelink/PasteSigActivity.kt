package com.example.paper_slide.ui.signaturelink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.paper_slide.R

class PasteSigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paste_sig)

        val backBtn : ImageView =findViewById(R.id.btnBack)

        backBtn.setOnClickListener {
            finish()
        }

    }


}