package com.tiksta.test4.home

import android.content.Intent
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.tiksta.test4.R
import com.tiksta.test4.base.BaseActivity
import com.tiksta.test4.post.PostActivity
import com.tiksta.test4.post.Utils
import kotlinx.android.synthetic.main.activity_post_tabs.*


class HomeActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_home

    override val bottomNavigationMenuItemId: Int
        get() = R.id.action_home
//        get() = R.id.action_post

    fun clickTikTok(view: View){
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
    }

    fun clickInstagram(view: View){
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
    }
}
