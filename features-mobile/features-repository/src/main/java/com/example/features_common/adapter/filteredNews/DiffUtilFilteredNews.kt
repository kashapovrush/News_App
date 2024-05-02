package com.example.features_common.adapter.filteredNews

import androidx.recyclerview.widget.DiffUtil
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.api.modelsDto.NewsHeadlinesDto

class DiffUtilFilteredNews: DiffUtil.ItemCallback<NewsHeadlines>() {
    override fun areItemsTheSame(oldItem: NewsHeadlines, newItem: NewsHeadlines): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: NewsHeadlines, newItem: NewsHeadlines): Boolean {
        return newItem == oldItem
    }
}