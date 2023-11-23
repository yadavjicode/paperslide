package com.example.paper_slide.ui.Signature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivitySignatureBinding
import com.example.paper_slide.ui.createasignature.CreateSignatureActivity

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
}