package com.example.paper_slide.ui.createpresentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityPresentationBinding
import com.example.paper_slide.ui.home.Home

class Presentation : AppCompatActivity() {
    private lateinit var binding : ActivityPresentationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presentation)

        initViews()
    }

    private fun initViews() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Explicitly navigate to the MainActivity (home screen)
        val intent = Intent(this, Home::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Optional: Call finish() to close the current activity if needed
    }


}