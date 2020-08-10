package com.tiksta.test4.home

import com.tiksta.test4.base.BaseActivity
import com.tiksta.test4.R

class HomeActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_home

    override val bottomNavigationMenuItemId: Int
        get() = R.id.action_home
}
