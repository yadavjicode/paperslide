package com.example.paper_slide.ui.otp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityOtpBinding

class Otp : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private lateinit var otpViewModel: OTPViewModel
    private var context = this@Otp
    private var TAG = "OTPLog"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(context, R.layout.activity_otp)

        otpViewModel = ViewModelProvider(
            context,
            OTPVMFactory(applicationContext)
        )[OTPViewModel::class.java]
        initViews()
    }


    private fun initViews() {
        val email = intent.getStringExtra("email")
        val editTexts = listOf(binding.etOTP1, binding.etOTP2, binding.etOTP3, binding.etOTP4)
        for (i in 0 until editTexts.size - 1) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (editTexts[i].text.length == 1) {
                        editTexts[i + 1].requestFocus()
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
        otpViewModel.jobSchedule(binding.CountDownText, this@Otp)

        binding.otpButton.setOnClickListener {
            try {
                val otp1 = binding.etOTP1.text.toString()
                val otp2 = binding.etOTP2.text.toString()
                val otp3 = binding.etOTP3.text.toString()
                val otp4 = binding.etOTP4.text.toString()
                val otp = otp1 + otp2 + otp3 + otp4
                Log.d(TAG, "initViews: ${otp.toInt()}")

                if (otp1.isEmpty() || otp2.isEmpty() || otp3.isEmpty() || otp4.isEmpty()) {
                    Toast.makeText(context, "OTP cannot be Empty", Toast.LENGTH_SHORT).show()
                } else {
                    otpViewModel.validateForgetPassword(email.toString(), otp.toInt())
                }
            } catch (e: Exception) {
                Log.d(TAG, "initViews: ${e.message}")
            }
        }
    }
    }
