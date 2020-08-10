package com.tiksta.test4.post

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.tiksta.test4.base.BaseActivity
import com.tiksta.test4.R
import kotlinx.android.synthetic.main.activity_post_tabs.*

class PostActivity : BaseActivity() {
    private lateinit var viewPager : ViewPager
    private lateinit var adapter : ViewPagerAdapter
    private lateinit var tabLayout : TabLayout

    override val layoutId: Int
        get() = R.layout.activity_post

    override val bottomNavigationMenuItemId: Int
        get() = R.id.action_post

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager = findViewById(R.id.pager)
        adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(pager)

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Utils.setTabPosition(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}

/*
ExpandableTransformationWidget
ExpandableListView.OnGroupExpandListener
ExpandableListAdapter
ExpandableListView.OnGroupCollapseListener


https://www.youtube.com/watch?v=QU5-IpnEub4

https://developer.android.com/guide/navigation/navigation-swipe-view

https://www.youtube.com/watch?v=h4HwU_ENXYM

https://www.youtube.com/watch?v=hbMqd0XRN34
 */