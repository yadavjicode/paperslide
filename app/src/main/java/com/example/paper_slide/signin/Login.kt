package com.example.paper_slide.signin

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
  //  private lateinit var callbackManager: CallbackManager
    private var context = this@Login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initview()
    }


    private fun initview(){
        binding.signInBtn.setOnClickListener {
            validateViews()
        }

    }

    private fun validateViews() {
        val password = binding.getPassword.text
        val email = binding.getEmail.text
        if (email?.isNotEmpty()!! && password?.isNotEmpty()!!) {
            /* lifecycleScope.launch {
               signInViewModel.validateSignIn(
                    email.toString(),
                    password.toString(),
                   // binding.progressBar
                )
            }*/
        } else if (email.isEmpty()) {
            binding.getEmail.error = "Please Enter Your Email"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.getEmail.error = "Please Enter a Valid Email"
        } else if (password?.isEmpty()!!) {
            binding.getPassword.error = "Please Enter Your Password"
        } else if (password.length <= 4) {
            binding.getPassword.error = "Password cannot be shorter than 4 Alphabets"
        }
    }
}