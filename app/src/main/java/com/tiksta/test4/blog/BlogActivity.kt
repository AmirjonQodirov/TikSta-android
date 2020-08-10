package com.tiksta.test4.blog

import com.tiksta.test4.base.BaseActivity
import com.tiksta.test4.R

class BlogActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_blog

    override val bottomNavigationMenuItemId: Int
        get() = R.id.action_blog
}
