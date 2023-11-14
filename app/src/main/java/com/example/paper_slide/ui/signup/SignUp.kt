package com.example.paper_slide.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.databinding.DataBindingUtil
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityLoginBinding
import com.example.paper_slide.databinding.ActivitySignUpBinding
import com.example.paperslide.util.Validate


class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val context=this@SignUp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding=DataBindingUtil.setContentView(context,R.layout.activity_sign_up)

        initView()

    }

    private fun initView() {
        binding.btnSignUp.setOnClickListener{

            validateViews()
        }
    }

    private fun validateViews() {
        val name= binding.etFName.text.toString()
        val email = binding.etEmail.text.toString()
        val mobile = binding.etMobile.text.toString()
        val password = binding.etPassword.text.toString()
        val validate = Validate()
        if(name.isEmpty()){
            binding.etFName.error="Please Enter First Name"
        }else if(email.isEmpty()){
            binding.etEmail.error="Please Enter email"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error="Enter Valid Email Id"
        }else if(mobile.isEmpty() || mobile.length <= 9 ){
            binding.etMobile.error="Enter Valid Mobile Number"
        }else if(password.isEmpty() || password.length <= 8 || !validate.validatePassword(password)){
            binding.etPassword.error="Enter valid password(password should contain lower case , upper case,numbers and symbols)"
        }else{

        }




    }


}


