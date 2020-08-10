package com.tiksta.test4

class BlogActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_blog

    override val bottomNavigationMenuItemId: Int
        get() = R.id.action_blog
}