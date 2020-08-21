package com.tiksta.ads.home

import android.content.Intent
import android.view.View
import com.tiksta.ads.R
import com.tiksta.ads.base.BaseActivity
import com.tiksta.ads.post.PostActivity


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
