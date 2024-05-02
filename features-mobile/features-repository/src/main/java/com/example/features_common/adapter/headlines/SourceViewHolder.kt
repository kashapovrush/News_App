package com.example.features_common.adapter.headlines

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.features_common.R

class SourceViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    val imageSource = view.findViewById<ImageView>(R.id.source_image)
    val sourceName = view.findViewById<TextView>(R.id.source_name)
    val aboutSource = view.findViewById<TextView>(R.id.about_source)
}