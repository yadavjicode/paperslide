package com.example.paper_slide.ui.preview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.paper_slide.R
import com.google.android.material.tabs.TabLayout

class preview : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var  viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        tabLayout=findViewById<TabLayout>(R.id.tablayout)
         viewPager=findViewById<ViewPager>(R.id.viewpage)

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


