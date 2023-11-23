package com.example.paper_slide.ui.preview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityPreviewBinding
import com.google.android.material.tabs.TabLayout

class preview : AppCompatActivity() {
    private lateinit var binding : ActivityPreviewBinding
    private lateinit var previewViewModel: PreviewViewModel
    private  val context = this@preview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,R.layout.activity_preview)

        previewViewModel = ViewModelProvider(context,
            PreviewVMFactory(applicationContext)
        )[PreviewViewModel::class.java]

        val tabLayout=binding.tabLayout
        val viewPager=binding.viewpager

        tabLayout.addTab(tabLayout.newTab().setText("Text"))
        tabLayout.addTab(tabLayout.newTab().setText("Image"))
        tabLayout.tabGravity=TabLayout.GRAVITY_FILL

        val adapter =FragmentAdapter(this,supportFragmentManager,tabLayout.tabCount)

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
}


