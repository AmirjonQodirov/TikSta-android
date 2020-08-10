package com.tiksta.test4.info

import com.tiksta.test4.base.BaseActivity
import com.tiksta.test4.R

class InfoActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_info

    override val bottomNavigationMenuItemId: Int
        get() = R.id.action_info
}
