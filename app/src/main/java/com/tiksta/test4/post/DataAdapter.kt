package com.tiksta.test4.post

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tiksta.test4.R


internal class DataAdapter(context: Context?, private val phones: MutableList<String>?) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val phone = phones?.get(position)
        holder.nameView.setText(phone.toString())
    }

    override fun getItemCount(): Int {
        if (phones != null) {
            return phones.size
        }else{
            return 0
        }
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView

        init {
            nameView = view.findViewById(R.id.tag_name)
        }
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}