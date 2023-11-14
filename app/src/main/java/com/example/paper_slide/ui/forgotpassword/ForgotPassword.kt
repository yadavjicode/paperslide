package com.example.paper_slide.ui.forgotpassword

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityForgotPasswordBinding
import com.example.paperslide.util.Validate

class ForgotPassword : AppCompatActivity() {
    private lateinit var binding:ActivityForgotPasswordBinding

    private val context=this@ForgotPassword
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        binding=DataBindingUtil.setContentView(context,R.layout.activity_forgot_password)
        initviews()

    }

    private fun initviews() {
        binding.fpButton.setOnClickListener {
            validateviews()
        }
    }

    private fun validateviews() {
        val email =binding.fpEmail.text.toString()

        //val validate =Validate()

        if(email.isEmpty()){
            binding.fpEmail.error="Please Enter email"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.fpEmail.error="Enter Valid Email Id"}
       else{

        }
    }
}