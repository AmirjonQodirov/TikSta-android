package com.tiksta.test4.blog

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.r0adkll.slidr.Slidr
import com.tiksta.test4.R
import kotlinx.android.synthetic.main.activity_blog_page.*

class BlogPage : AppCompatActivity() {
    private var resultTagsListTikTok: ArrayList<String> = ArrayList()
    private var resultTagsListInstagram: ArrayList<String> = ArrayList()

    private fun updateResult(tags: ArrayList<String>, resultWithHashTagsBlogPage: TextView) {
        val result: StringBuilder = StringBuilder()
        for (tag in tags) {
            result.append("#$tag ")
        }
        if (result.isNotEmpty()) {
            result.deleteCharAt(result.length - 1)
        }

        resultWithHashTagsBlogPage.text = result
    }

    private fun showChips(
        resultTagsList: ArrayList<String>, div2BlogPage: ConstraintLayout,
        copyToClipboardBlogPage: Button, backList: Button,
        resultWithHashTagsBlogPage: TextView, chipGroupBlogPage: ChipGroup
    ) {
        if (resultTagsList.isEmpty()) {
            div2BlogPage.visibility = View.GONE
            copyToClipboardBlogPage.visibility = View.GONE
            backList.visibility = View.VISIBLE

            return
        }

        val inflater: LayoutInflater = LayoutInflater.from(this)

        updateResult(resultTagsList, resultWithHashTagsBlogPage)

        for (tag in resultTagsList) {
            val chip: Chip = inflater.inflate(R.layout.chip_item, null, false) as Chip
            chip.text = tag
            chip.setOnCloseIconClickListener {
                chipGroupBlogPage.removeView(chip)

                if (resultTagsList.size == 1) {
                    Toast.makeText(
                        this,
                        "All hashtags have been removed!",
                        Toast.LENGTH_SHORT
                    ).show()

                    div2BlogPage.visibility = View.GONE
                    copyToClipboardBlogPage.visibility = View.GONE
                    backList.visibility = View.VISIBLE
                }

                resultTagsList.remove(tag)

                updateResult(resultTagsList, resultWithHashTagsBlogPage)
            }

            chipGroupBlogPage.addView(chip)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = intent.getStringExtra("BLOG_NAME")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        setContentView(R.layout.activity_blog_page)

        val arrayTagsTikTok = intent.getStringArrayExtra("BLOG_LIST_OF_TAGS_TIKTOK")
        val arrayTagsInstagram = intent.getStringArrayExtra("BLOG_LIST_OF_TAGS_INSTAGRAM")

        if (arrayTagsTikTok != null) {
            for (tag in arrayTagsTikTok) {
                resultTagsListTikTok.add(tag)
            }
        }
        if (arrayTagsInstagram != null) {
            for (tag in arrayTagsInstagram) {
                resultTagsListInstagram.add(tag)
            }
        }

        if (savedInstanceState == null) {
            showChips(
                resultTagsListTikTok,
                div2BlogPageTikTok,
                copyToClipboardBlogPageTikTok,
                backListTikTok,
                resultWithHashTagsBlogPageTikTok,
                chipGroupBlogPageTikTok
            )
            showChips(
                resultTagsListInstagram,
                div2BlogPageInstagram,
                copyToClipboardBlogPageInstagram,
                backListInstagram,
                resultWithHashTagsBlogPageInstagram,
                chipGroupBlogPageInstagram
            )
        } else {
            val tiktokArray = savedInstanceState.getStringArrayList("TAGS_CHIPS_TIKTOK")
            if (tiktokArray != null) {
                resultTagsListTikTok = tiktokArray
            }

            val instagramArray = savedInstanceState.getStringArrayList("TAGS_CHIPS_INSTAGRAM")
            if (instagramArray != null) {
                resultTagsListInstagram = instagramArray
            }

            showChips(
                resultTagsListTikTok,
                div2BlogPageTikTok,
                copyToClipboardBlogPageTikTok,
                backListTikTok,
                resultWithHashTagsBlogPageTikTok,
                chipGroupBlogPageTikTok
            )
            showChips(
                resultTagsListInstagram,
                div2BlogPageInstagram,
                copyToClipboardBlogPageInstagram,
                backListInstagram,
                resultWithHashTagsBlogPageInstagram,
                chipGroupBlogPageInstagram
            )
        }

        copyToClipboardBlogPageTikTok.setOnClickListener {
            val clipboard =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                "text",
                findViewById<TextView>(R.id.resultWithHashTagsBlogPageTikTok).text.toString()
            )
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "Result copied to clipboard!", Toast.LENGTH_SHORT).show()
        }

        copyToClipboardBlogPageInstagram.setOnClickListener {
            val clipboard =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                "text",
                findViewById<TextView>(R.id.resultWithHashTagsBlogPageInstagram).text.toString()
            )
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "Result copied to clipboard!", Toast.LENGTH_SHORT).show()
        }

        backListTikTok.setOnClickListener {
            if (arrayTagsTikTok != null) {
                for (tag in arrayTagsTikTok) {
                    resultTagsListTikTok.add(tag)
                }
            }
            showChips(
                resultTagsListTikTok,
                div2BlogPageTikTok,
                copyToClipboardBlogPageTikTok,
                backListTikTok,
                resultWithHashTagsBlogPageTikTok,
                chipGroupBlogPageTikTok
            )

            backListTikTok.visibility = View.GONE
            horizontalScrollViewBlogPageTikTok.visibility = View.VISIBLE
            copyToClipboardBlogPageTikTok.visibility = View.VISIBLE
            div2BlogPageTikTok.visibility = View.VISIBLE
        }

        backListInstagram.setOnClickListener {
            if (arrayTagsInstagram != null) {
                for (tag in arrayTagsInstagram) {
                    resultTagsListInstagram.add(tag)
                }
            }
            showChips(
                resultTagsListInstagram,
                div2BlogPageInstagram,
                copyToClipboardBlogPageInstagram,
                backListInstagram,
                resultWithHashTagsBlogPageInstagram,
                chipGroupBlogPageInstagram
            )

            backListInstagram.visibility = View.GONE
            horizontalScrollViewBlogPageInstagram.visibility = View.VISIBLE
            copyToClipboardBlogPageInstagram.visibility = View.VISIBLE
            div2BlogPageInstagram.visibility = View.VISIBLE
        }

        Slidr.attach(this)

        MobileAds.initialize(this)

        val adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
            .forUnifiedNativeAd { unifiedNativeAd -> //the native ad will be available inside this method  (unifiedNativeAd)
                val unifiedNativeAdView = layoutInflater.inflate(
                    R.layout.native_ad_layout,
                    null
                ) as UnifiedNativeAdView
                mapUnifiedNativeAdToLayout(unifiedNativeAd, unifiedNativeAdView)
                val nativeAdLayout = findViewById<FrameLayout>(R.id.native_ad_blog_page)
                nativeAdLayout.removeAllViews()
                nativeAdLayout.addView(unifiedNativeAdView)
            }
            .build()
        adLoader.loadAd(AdRequest.Builder().build())


    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArrayList("TAGS_CHIPS_TIKTOK", resultTagsListTikTok)
        outState.putStringArrayList("TAGS_CHIPS_INSTAGRAM", resultTagsListInstagram)

        super.onSaveInstanceState(outState)
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

}