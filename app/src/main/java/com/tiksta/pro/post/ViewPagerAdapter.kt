package com.tiksta.pro.post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val titles = arrayOf("TikTok", "Instagram")

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getItem(position: Int): Fragment {
        return BlankFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}
