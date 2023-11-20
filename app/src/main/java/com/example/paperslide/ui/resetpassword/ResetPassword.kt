package com.example.paperslide.ui.resetpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paperslide.R
import com.example.paperslide.databinding.ActivityResetPasswordBinding
import kotlinx.coroutines.launch

class ResetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var resetPasswordViewModel: ResetPasswordViewModel
    private var context = this@ResetPassword
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            context, R.layout.activity_reset_password
        )


        resetPasswordViewModel = ViewModelProvider(
            context,
            ResetPasswordVMFactory(applicationContext)
        )[ResetPasswordViewModel::class.java]
        binding.rpButton.setOnClickListener {
            initViews()
        }

    }

    private fun initViews() {
      //  val email = intent.getStringExtra("email")
        val newPassword = binding.newPassword.text.toString()
        val confirmPassword = binding.newPassword1.text.toString()
            if (confirmPassword != newPassword) {
                binding.newPassword.error = "Password Does not Match"
            } else {
                lifecycleScope.launch {
                    resetPasswordViewModel.validateResetPass(
                      //  email.toString(),
                        confirmPassword,
                        binding.progressBar,
                        context
                    )
                }
            }


    }
}