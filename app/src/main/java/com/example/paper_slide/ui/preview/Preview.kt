package com.example.paper_slide.ui.preview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityPreviewBinding
import com.example.paper_slide.ui.summarize.Summarize
import com.example.paper_slide.ui.textTranslate.TextTranslateActivity
import com.google.android.material.tabs.TabLayout

class Preview : AppCompatActivity() {
    private lateinit var binding : ActivityPreviewBinding
    private lateinit var previewViewModel: PreviewViewModel
    private  val context = this@Preview
    private lateinit var summeryText :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,R.layout.activity_preview)

        previewViewModel = ViewModelProvider(context,
            PreviewVMFactory(applicationContext)
        )[PreviewViewModel::class.java]

        initViewPager()
        initViews()

    }

    private fun initViewPager() {
        val tabLayout=binding.tabLayout
        val viewPager=binding.viewpager

        summeryText = intent.getStringExtra("ocrtext").toString()

        tabLayout.addTab(tabLayout.newTab().setText("Text"))
        tabLayout.addTab(tabLayout.newTab().setText("Image"))
        tabLayout.tabGravity=TabLayout.GRAVITY_FILL

        val adapter =FragmentAdapter(this,supportFragmentManager,tabLayout.tabCount,summeryText)

        viewPager.adapter= adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem =tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


    }

    private fun initViews() {
        binding.summarizeBtn.setOnClickListener {
            val intent = Intent(context,Summarize ::class.java)
            intent.putExtra("summaryData",summeryText)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            context.startActivity(intent)
        }

        binding.translateIconIV.setOnClickListener {
            val intent = Intent(context,TextTranslateActivity ::class.java)
            intent.putExtra("summaryData",summeryText)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            context.startActivity(intent)
        }
    }
}


