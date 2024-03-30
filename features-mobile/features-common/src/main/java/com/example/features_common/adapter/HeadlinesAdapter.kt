package com.example.features_common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.features_common.R
import com.kashapovrush.api.modelsDto.NewsHeadlines

class HeadlinesAdapter(var context: Context, val onClickListenerHeadlinesAdapter: OnClickListenerHeadlinesAdapter) :
    ListAdapter<NewsHeadlines, HeadlinesAdapter.HeadlinesViewHolder>(
        DiffUtilHeadlinesCallback()
    ) {

//    var onClickHeadlinesListener: ((NewsHeadlines) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        return HeadlinesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_news_post,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {
        val post = getItem(position)
        Glide.with(context).load(post.urlToImage).placeholder(null)
            .into(holder.contentImage)
        holder.contentIcon.setImageResource(com.example.palette.R.drawable.ic_source_icon)
        holder.description.text = post.description
        holder.contentSourceName.text = post.source.name


        when (post.source.name) {
            "BBC News", "BBC Sport" -> holder.contentIcon.setImageResource(com.example.palette.R.drawable.bbc)
            "Bloomberg" -> holder.contentIcon.setImageResource(com.example.palette.R.drawable.bloomberg)
            "CNN" -> holder.contentIcon.setImageResource(com.example.palette.R.drawable.cnn)
            "Fox News", "Fox Sports" -> holder.contentIcon.setImageResource(com.example.palette.R.drawable.fox_news)
            "CNBC" -> holder.contentIcon.setImageResource(com.example.palette.R.drawable.cnbc)
            else -> {}
        }

        holder.view.setOnClickListener {
            onClickListenerHeadlinesAdapter.onClick(post)

        }
    }

    interface OnClickListenerHeadlinesAdapter {

        fun onClick(post: NewsHeadlines)
    }

    class HeadlinesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val contentImage = view.findViewById<ImageView>(R.id.content_image)
        val contentIcon = view.findViewById<ImageView>(R.id.content_icon)
        val contentSourceName = view.findViewById<TextView>(R.id.content_source_name)
        val description = view.findViewById<TextView>(R.id.content_description)
    }
}