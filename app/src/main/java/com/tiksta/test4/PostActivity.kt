package com.tiksta.test4

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
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