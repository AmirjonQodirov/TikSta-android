package com.tiksta.test4.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.tiksta.test4.R
import com.tiksta.test4.base.BaseActivity

class InfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)

        val adLoader = AdLoader.Builder(this, "ca-app-pub-2632952731797743/6205919385")
            .forUnifiedNativeAd { unifiedNativeAd -> //the native ad will be available inside this method  (unifiedNativeAd)
                val unifiedNativeAdView = layoutInflater.inflate(
                    R.layout.native_ad_layout,
                    null
                ) as UnifiedNativeAdView
                mapUnifiedNativeAdToLayout(unifiedNativeAd, unifiedNativeAdView)
                val nativeAdLayout = findViewById<FrameLayout>(R.id.native_ad)
                nativeAdLayout.removeAllViews()
                nativeAdLayout.addView(unifiedNativeAdView)
            }
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    fun mapUnifiedNativeAdToLayout(
        adFromGoogle: UnifiedNativeAd,
        myAdView: UnifiedNativeAdView
    ) {
        val mediaView: MediaView = myAdView.findViewById(R.id.ad_media)
        myAdView.mediaView = mediaView
        myAdView.headlineView = myAdView.findViewById(R.id.ad_headline)
        myAdView.bodyView = myAdView.findViewById(R.id.ad_body)
        myAdView.callToActionView = myAdView.findViewById(R.id.ad_call_to_action)
        myAdView.iconView = myAdView.findViewById(R.id.ad_icon)
        myAdView.priceView = myAdView.findViewById(R.id.ad_price)
        myAdView.starRatingView = myAdView.findViewById(R.id.ad_rating)
        myAdView.storeView = myAdView.findViewById(R.id.ad_store)
        myAdView.advertiserView = myAdView.findViewById(R.id.ad_advertiser)
        (myAdView.headlineView as TextView).text = adFromGoogle.headline
        if (adFromGoogle.body == null) {
            myAdView.bodyView.visibility = View.GONE
        } else {
            (myAdView.bodyView as TextView).text = adFromGoogle.body
        }
        if (adFromGoogle.callToAction == null) {
            myAdView.callToActionView.visibility = View.GONE
        } else {
            (myAdView.callToActionView as Button).setText(adFromGoogle.callToAction)
        }
        if (adFromGoogle.icon == null) {
            myAdView.iconView.visibility = View.GONE
        } else {
            (myAdView.iconView as ImageView).setImageDrawable(
                adFromGoogle.icon.drawable
            )
        }
        if (adFromGoogle.price == null) {
            myAdView.priceView.visibility = View.GONE
        } else {
            (myAdView.priceView as TextView).text = adFromGoogle.price
        }
        if (adFromGoogle.starRating == null) {
            myAdView.starRatingView.visibility = View.GONE
        } else {
            (myAdView.starRatingView as RatingBar).rating = adFromGoogle.starRating.toFloat()
        }
        if (adFromGoogle.store == null) {
            myAdView.storeView.visibility = View.GONE
        } else {
            (myAdView.storeView as TextView).text = adFromGoogle.store
        }
        if (adFromGoogle.advertiser == null) {
            myAdView.advertiserView.visibility = View.GONE
        } else {
            (myAdView.advertiserView as TextView).text = adFromGoogle.advertiser
        }
        myAdView.setNativeAd(adFromGoogle)
    }

    fun gitMuhammadjon(view: View) {
        goToUrl("http://github.com/MrHakimov/")
    }

    fun gitAmir(view: View) {
        goToUrl("http://github.com/AmirjonQodirov/")
    }

    fun gitBehruz(view: View) {
        goToUrl("http://github.com/MansurovB-source/")
    }

    fun mailMuhammadjon(view: View) {
        goToUrl("mailto:hakimov0777@gmail.com")
    }

    fun mailAmir(view: View) {
        goToUrl("mailto:amirqodirov8383@gmail.com")
    }

    fun mailBehruz(view: View) {
        goToUrl("mailto:bekha.m2000.bm@gmail.com")
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
