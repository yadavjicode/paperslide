package com.example.paper_slide.ui.tc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityTcBinding
import com.example.paper_slide.ui.home.Home
import com.example.paper_slide.ui.signatureoptions.SignatureActivity
import kotlinx.coroutines.launch

class TCActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTcBinding
    val context = this@TCActivity
    private lateinit var tcViewModel: TCViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,
            R.layout.activity_tc)

        tcViewModel = ViewModelProvider(context,
            TCVMFactory(applicationContext)
        )[TCViewModel::class.java]
        iniViews()
    }

    private fun iniViews() {

        binding.rejectBtn.setOnClickListener {
            val intent = Intent(this@TCActivity, Home::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.acceptBtn.setOnClickListener {
            val intent = Intent(this@TCActivity, Home::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        lifecycleScope.launch {
            tcViewModel.validateTC(binding.TCTV)
        }
    }
}