package com.example.paper_slide.ui.preview
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class FragmentAdapter(var context: Context, fm:FragmentManager, var totalTabs:Int ):FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return totalTabs

    }

    override fun getItem(position: Int): Fragment {
        return  when (position) {
            0 -> {
                TextFragment()
            }

            1 -> {
                ImageFragment()
            }

            else -> getItem(position)
        }
    }
}
