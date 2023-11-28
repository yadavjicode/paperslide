package com.example.paper_slide.ui.Signature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivitySignatureBinding
import com.example.paper_slide.ui.createasignature.CreateSignatureActivity
import com.example.paper_slide.ui.home.Home

class Signature : AppCompatActivity() {
    private var context =this@Signature
    private lateinit var binding :ActivitySignatureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =DataBindingUtil.setContentView(context,R.layout.activity_signature)
       // setContentView(R.layout.activity_signature)




 intview()
    }

    private fun intview() {
        binding.importSign.setOnClickListener {
            val intent = Intent(this@Signature, CreateSignatureActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
    override fun onBackPressed() {
        // Explicitly navigate to the MainActivity (home screen)
        val intent = Intent(this, Home::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Optional: Call finish() to close the current activity if needed
    }
}