package com.example.features_filter_news.adapter.date_adapter

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.features_filter_news.R

class CalendarViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val day = view.findViewById<TextView>(R.id.calendar_cell)

}