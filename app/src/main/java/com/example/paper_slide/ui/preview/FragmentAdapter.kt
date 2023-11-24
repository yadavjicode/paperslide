package com.example.paper_slide.ui.preview
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

internal class FragmentAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    val summeryText: String?
):FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return totalTabs

    }

    override fun getItem(position: Int): Fragment {
        return  when (position) {
            0 -> {
                val textFragment = TextFragment()
                val bundle = Bundle()
                bundle.putString("summeryText",summeryText)
                textFragment.arguments= bundle
                textFragment
            }

            1 -> {
                ImageFragment()
            }

            else -> getItem(position)
        }
    }
}
