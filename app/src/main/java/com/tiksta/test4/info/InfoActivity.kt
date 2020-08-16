package com.tiksta.test4.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.tiksta.test4.R
import com.tiksta.test4.base.BaseActivity


class InfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun gitMuhammad(view: View) {
        goToUrl ( "http://github.com/MrHakimov/");
    }

    fun gitAmir(view: View) {
        goToUrl ( "http://github.com/AmirjonQodirov/");
    }
    fun mailAmir(view: View){
        goToUrl("mailto:amirqodirov8383@gmail.com")
    }
    fun mailMuhammad(view: View){
        goToUrl("mailto:hakimov0777@gmail.com")
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
//        get() = R.id.action_post
}
