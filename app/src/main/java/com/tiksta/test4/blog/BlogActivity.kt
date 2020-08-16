package com.tiksta.test4.blog

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiksta.test4.base.BaseActivity
import com.tiksta.test4.R
import kotlinx.android.synthetic.main.activity_blog.*

// Save scroll position
// https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state

class BlogActivity : BaseActivity(),  OnBlogItemClickListener{

    lateinit var blogList: ArrayList<Blog>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blogList = ArrayList()
        addBlogs()

        blogRecycler.layoutManager = LinearLayoutManager(this)
        blogRecycler.addItemDecoration(DividerItemDecoration(this,1))
        blogRecycler.adapter = BlogAdapter(blogList,this)
    }

    fun addBlogs(){
        blogList.add(Blog("Toyota",arrayOf("ttok","as"),arrayOf("ins","as")))
        blogList.add(Blog("Hyundai", arrayOf("ttok","a"),arrayOf("ins","as")))

    }

    fun convertTagsToString(tags: ArrayList<String>): String {
        var result: String = ""

        for (tag in tags) {
            result += "#$tag "
        }

        return result
    }

    override fun onItemClick(item: Blog, position: Int) {
        val intent = Intent(this, BlogPage::class.java)
        intent.putExtra("BLOG_NAME", item.name)
        intent.putExtra("BLOG_LIST_OF_TAGS_TIKTOK", item.listOfTagsTikTok)
        intent.putExtra("BLOG_LIST_OF_TAGS_INSTAGRAM", item.listOfTagsInstagram)
        startActivity(intent)


    }

    override val layoutId: Int
        get() = R.layout.activity_blog

    override val bottomNavigationMenuItemId: Int
        get() = R.id.action_blog
}
