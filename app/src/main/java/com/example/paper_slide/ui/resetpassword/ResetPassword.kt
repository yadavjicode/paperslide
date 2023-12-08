package com.example.paper_slide.ui.resetpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityResetPasswordBinding
import com.example.paper_slide.ui.resetpassword.ResetPasswordVMFactory
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
        val newPassword = binding.newPassET.text.toString()
        val confirmPassword = binding.newPassConfirm.text.toString()
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

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}