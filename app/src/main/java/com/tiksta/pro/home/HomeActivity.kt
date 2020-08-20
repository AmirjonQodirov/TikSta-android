package com.tiksta.pro.home

import android.content.Intent
import android.view.View
import com.tiksta.pro.R
import com.tiksta.pro.base.BaseActivity
import com.tiksta.pro.post.PostActivity


class HomeActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_home

    override val bottomNavigationMenuItemId: Int
        get() = R.id.action_home

    fun clickTikTok(view: View){
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
    }

    fun clickInstagram(view: View){
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
    }
}
