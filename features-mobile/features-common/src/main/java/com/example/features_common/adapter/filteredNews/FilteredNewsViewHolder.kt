package com.example.features_common.adapter.filteredNews

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.features_common.R

class FilteredNewsViewHolder(val view: View): ViewHolder(view) {

    val contentImage = view.findViewById<ImageView>(R.id.content_image)
    val contentIcon = view.findViewById<ImageView>(R.id.content_icon)
    val contentSourceName = view.findViewById<TextView>(R.id.content_source_name)
    val description = view.findViewById<TextView>(R.id.content_description)
}