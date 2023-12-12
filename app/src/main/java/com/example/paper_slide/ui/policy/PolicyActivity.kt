package com.example.paper_slide.ui.policy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityPolicyBinding
import com.example.paper_slide.databinding.ActivityTcBinding
import com.example.paper_slide.ui.home.Home
import kotlinx.coroutines.launch

class PolicyActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPolicyBinding
    private lateinit var policyViewModel: PolicyViewModel
    private val context = this@PolicyActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,R.layout.activity_policy)
        policyViewModel = ViewModelProvider(context,
                            PolicyVMFactory(applicationContext)
        )[PolicyViewModel::class.java]

        initView()

    }

    private fun initView() {

        binding.rejectBtn.setOnClickListener {
            val intent = Intent(context , Home::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

       binding.acceptBtn.setOnClickListener {
           val intent = Intent(context , Home::class.java)
           intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
           startActivity(intent)
           finish()
       }

        lifecycleScope.launch {
            policyViewModel.validatePolicy(binding.policyTV)

        }
    }
}