package com.tiksta.test4

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val titles = arrayOf("TikTok", "Instagram")

    override fun getItem(position: Int): Fragment {
//        Toast.makeText(this, )
        return BlankFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
//        Utils.setTabPosition(position)
//        println(position)
        return titles[position]
    }
}
