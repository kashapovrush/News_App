package com.example.features_filter_news.adapter.year_adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.features_filter_news.R

class YearChooseViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    val yearCell = view.findViewById<TextView>(R.id.year_cell)
}