package com.tiksta.ads.base

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tiksta.ads.R
import com.tiksta.ads.blog.BlogActivity
import com.tiksta.ads.home.HomeActivity
import com.tiksta.ads.info.InfoActivity
import com.tiksta.ads.post.PostActivity

abstract class BaseActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    private var navigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(this.layoutId)
        navigationView = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigationView!!.setOnNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        updateNavigationBarState()
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    public override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationView!!.postDelayed({
            when (item.itemId) {
                R.id.action_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                }
                R.id.action_post -> {
                    startActivity(Intent(this, PostActivity::class.java))
                }
                R.id.action_blog -> {
                    startActivity(Intent(this, BlogActivity::class.java))
                }
                R.id.action_info -> {
                    startActivity(Intent(this, InfoActivity::class.java))
                }
            }
            finish()
        }, 0)
        return true
    }

    private fun updateNavigationBarState() {
        selectBottomNavigationBarItem(this.bottomNavigationMenuItemId)
    }

    private fun selectBottomNavigationBarItem(itemId: Int) {
        val item: MenuItem = navigationView!!.menu.findItem(itemId)
        item.isChecked = true
    }

    // this is to return which layout (activity) needs to display when clicked on tabs.
    abstract val layoutId: Int

    // Which menu item selected and change the state of that menu item
    abstract val bottomNavigationMenuItemId: Int
}