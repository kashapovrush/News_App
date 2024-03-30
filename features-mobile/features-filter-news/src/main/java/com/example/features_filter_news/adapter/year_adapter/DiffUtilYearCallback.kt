package com.example.features_filter_news.adapter.year_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.features_filter_news.model.Year

class DiffUtilYearCallback: DiffUtil.ItemCallback<Year>() {
    override fun areItemsTheSame(oldItem: Year, newItem: Year): Boolean {
        return oldItem.value == newItem.value
    }

    override fun areContentsTheSame(oldItem: Year, newItem: Year): Boolean {
        return oldItem == newItem
    }
}