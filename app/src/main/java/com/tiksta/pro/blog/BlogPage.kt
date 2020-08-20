package com.tiksta.pro.blog

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.r0adkll.slidr.Slidr
import com.tiksta.pro.R
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
                        getString(R.string.all_hashtags_removed),
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

            Toast.makeText(this, getString(R.string.result_copied), Toast.LENGTH_SHORT).show()
        }

        copyToClipboardBlogPageInstagram.setOnClickListener {
            val clipboard =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                "text",
                findViewById<TextView>(R.id.resultWithHashTagsBlogPageInstagram).text.toString()
            )
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, getString(R.string.result_copied), Toast.LENGTH_SHORT).show()
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArrayList("TAGS_CHIPS_TIKTOK", resultTagsListTikTok)
        outState.putStringArrayList("TAGS_CHIPS_INSTAGRAM", resultTagsListInstagram)

        super.onSaveInstanceState(outState)
    }
}
