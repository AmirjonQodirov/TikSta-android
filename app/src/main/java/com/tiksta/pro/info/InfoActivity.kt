package com.tiksta.pro.info

import android.content.Intent
import android.net.Uri
import android.view.View
import com.tiksta.pro.R
import com.tiksta.pro.base.BaseActivity

class InfoActivity : BaseActivity() {
    fun gitMuhammadjon(view: View) {
        goToUrl(getString(R.string.github_muhammadjon_url))
    }

    fun gitAmir(view: View) {
        goToUrl(getString(R.string.github_amirjon_url))
    }

    fun gitBehruz(view: View) {
        goToUrl(getString(R.string.github_behruz_url))
    }

    fun mailMuhammadjon(view: View) {
        goToUrl(getString(R.string.mail_muhammadjon_url))
    }

    fun mailAmir(view: View) {
        goToUrl(getString(R.string.mail_amirjon_url))
    }

    fun mailBehruz(view: View) {
        goToUrl(getString(R.string.mail_behruz_url))
    }

    private fun goToUrl(url: String) {
        val uriUrl: Uri = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }

    override val layoutId: Int
        get() = R.layout.activity_info

    override val bottomNavigationMenuItemId: Int
        get() = R.id.action_info
}
