package com.tiksta.test4

class HomeActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_home

    override val bottomNavigationMenuItemId: Int
        get() = R.id.action_home
}