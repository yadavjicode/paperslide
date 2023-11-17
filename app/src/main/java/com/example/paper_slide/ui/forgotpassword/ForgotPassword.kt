package com.example.paper_slide.ui.forgotpassword

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityForgotPasswordBinding
import com.example.paperslide.util.Validate
import kotlinx.coroutines.launch

class ForgotPassword : AppCompatActivity() {
    private lateinit var binding:ActivityForgotPasswordBinding
 private lateinit var forgetPasswordViewModel :ForgotPasswordViewModel
    private val context=this@ForgotPassword
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        binding=DataBindingUtil.setContentView(context,R.layout.activity_forgot_password)
        initviews()

        forgetPasswordViewModel = ViewModelProvider(context,
            ForgotPasswordVMFactory(applicationContext)
        )[ForgotPasswordViewModel::class.java]

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
            lifecycleScope.launch {
                forgetPasswordViewModel.validateForgetPassword(email)
            }
        }
    }
}