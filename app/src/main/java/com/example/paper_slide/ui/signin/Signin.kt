package com.example.paper_slide.ui.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivitySigninBinding
import com.example.paper_slide.ui.forgotpassword.ForgotPassword
import com.example.paper_slide.ui.signup.SignUpActivity
import com.example.paper_slide.util.Validate
import com.facebook.CallbackManager
import kotlinx.coroutines.launch

class Signin : AppCompatActivity() {


    private lateinit var binding: ActivitySigninBinding

    private var context=this@Signin
    private lateinit var callbackManager: CallbackManager
   private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

      binding =  DataBindingUtil.setContentView(context,R.layout.activity_signin)
        callbackManager =CallbackManager.Factory.create()
        initViews()

           signInViewModel = ViewModelProvider(
               context,
               SignInVMFactory(context)
           )[SignInViewModel::class.java]



    }

    private fun initViews() {
        binding.siSignin.setOnClickListener {
            validateview()
        }
        binding.btnforgot.setOnClickListener{

            signInViewModel.startNewActivity(ForgotPassword::class.java)
        }
        binding.btnSignup.setOnClickListener {

            signInViewModel.startNewActivity(SignUpActivity::class.java)
        }
    }

    private fun validateview() {

        val email =binding.siEmail.text.toString()
        val password =binding.siPassword.text.toString()
        val validate =Validate()
        if(email.isEmpty()){
            binding.siEmail.error="Please Enter email"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.siEmail.error="Enter Valid Email Id"
        }else if(password.isEmpty() || password.length <= 8 ||!validate.validatePassword(password)){
            binding.siPassword.error="Enter valid password(password should contain lower case , upper case,numbers and symbols)"
        }else{
            lifecycleScope.launch {
                signInViewModel.validateSignIn(
                    email.toString(),
                    password.toString(),
                    binding.progressBar
                )
            }
        }

    }
}