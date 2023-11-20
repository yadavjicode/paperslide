package com.example.paperslide.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.paperslide.R
import com.example.paperslide.databinding.ActivitySplashBinding
//import com.example.paper_slide.signin.Login
import com.example.paperslide.ui.signin.Login
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding
    private var context=this@Splash


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        binding =DataBindingUtil.setContentView(context,R.layout.activity_splash)

        val animation = ScaleAnimation(
            0.5f,
            1.0f,
            0.5f,
            1.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        animation.duration=1000
        animation.fillAfter=true
        binding.splash.startAnimation(animation)


        lifecycleScope.launch {
            delay(3000)
            val iHome = Intent(this@Splash, Login::class.java)
            startActivity(iHome)
            finish()
        }


    }




}