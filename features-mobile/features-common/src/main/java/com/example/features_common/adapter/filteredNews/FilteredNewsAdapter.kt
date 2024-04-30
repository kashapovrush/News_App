package com.example.features_common.adapter.filteredNews

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.example.features_common.R
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.api.modelsDto.NewsHeadlinesDto

class FilteredNewsAdapter(private val context: Context) :
    PagingDataAdapter<NewsHeadlines, FilteredNewsViewHolder>(DiffUtilFilteredNews()) {

        var onClickListenerFilteredNews: ((NewsHeadlines?) -> Unit)? = null
    override fun onBindViewHolder(holder: FilteredNewsViewHolder, position: Int) {
        val post = getItem(position)

        Glide.with(context).load(post?.urlToImage).placeholder(null)
            .into(holder.contentImage)
        holder.contentIcon.setImageResource(com.example.palette.R.drawable.ic_source_icon)
        holder.description.text = post?.description
        holder.contentSourceName.text = post?.source?.name

        holder.view.setOnClickListener {
            onClickListenerFilteredNews?.invoke(post)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilteredNewsViewHolder {
        return FilteredNewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_news_post, parent, false
            )
        )
    }
}