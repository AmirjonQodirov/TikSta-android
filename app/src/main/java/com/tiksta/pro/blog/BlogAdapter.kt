package com.tiksta.pro.blog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tiksta.pro.R
import kotlinx.android.synthetic.main.blog_item.view.*

class BlogAdapter(
    private var items: ArrayList<Blog>,
    private var clickListener: OnBlogItemClickListener
) :
    RecyclerView.Adapter<BlogViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.blog_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.initialize(items[position], clickListener)
    }
}

class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var blogName = itemView.blog__name

    fun initialize(item: Blog, action: OnBlogItemClickListener) {
        blogName.text = item.name

        itemView.setOnClickListener {
            action.onItemClick(item, adapterPosition)
        }

    }

}

interface OnBlogItemClickListener {
    fun onItemClick(item: Blog, position: Int)
}
