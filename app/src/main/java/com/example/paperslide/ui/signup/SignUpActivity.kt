package com.example.paperslide.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paperslide.R
import com.example.paperslide.databinding.ActivitySignUpBinding
import com.example.paperslide.util.Validate
import com.facebook.CallbackManager
import kotlinx.coroutines.launch


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val context=this@SignUpActivity
    val TAG = "SignUpLogs"
    private lateinit var callbackManager: CallbackManager
    private lateinit var signUpViewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        binding=DataBindingUtil.setContentView(context,R.layout.activity_sign_up)
        callbackManager =CallbackManager.Factory.create()


        initView()

    }

    private fun initView() {
        binding.btnSignUp.setOnClickListener{

            validateViews()
        }
        signUpViewModel = ViewModelProvider(
            context,
            SignUpVMFactory(applicationContext)
        )[SignUpViewModel::class.java]
    }

    private fun validateViews() {
        val name= binding.etFName.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etMobile.text.toString()
        val password = binding.etPassword.text.toString()
        val validate = Validate()
        if(name.isEmpty()){
            binding.etFName.error="Please Enter First Name"
        }else if(email.isEmpty()){
            binding.etEmail.error="Please Enter email"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error="Enter Valid Email Id"
        }else if(phone.isEmpty() || phone.length <= 9 ){
            binding.etMobile.error="Enter Valid Mobile Number"
        }else if(password.isEmpty() || password.length <= 8 || !validate.validatePassword(password)){
            binding.etPassword.error="Enter valid password(password should contain lower case , upper case,numbers and symbols)"
        }else{
            lifecycleScope.launch {
                Log.d(TAG, "validateViews: $name $email $phone $password")
                signUpViewModel.validateSignUp(name, email, phone, password, this@SignUpActivity)
            }

        }




    }


}


