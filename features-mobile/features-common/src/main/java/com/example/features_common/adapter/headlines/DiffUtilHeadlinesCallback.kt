package com.example.features_common.adapter.headlines

import androidx.recyclerview.widget.DiffUtil
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.api.modelsDto.NewsHeadlinesDto

class DiffUtilHeadlinesCallback: DiffUtil.ItemCallback<NewsHeadlines>() {
    override fun areItemsTheSame(oldItem: NewsHeadlines, newItem: NewsHeadlines): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: NewsHeadlines, newItem: NewsHeadlines): Boolean {
        return oldItem == newItem
    }
}