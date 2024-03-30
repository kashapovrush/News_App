package com.example.features_common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.features_common.R
import com.kashapovrush.api.modelsDto.Source

class SourcesAdapter(val context: Context) : ListAdapter<Source, SourceViewHolder>(DiffUtilSourceCallback()) {

    var onCLickListenerItem: ((Source) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        return SourceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_source,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val item = getItem(position)

        holder.sourceName.text = item.name

        if (item.name == "BBC News" || item.name == "BBC Sport") {
            holder.imageSource.setImageResource(com.example.palette.R.drawable.bbc)
        } else if (item.name == "Bloomberg") {
            holder.imageSource.setImageResource(com.example.palette.R.drawable.bloomberg)
        } else if (item.name == "CNN") {
            holder.imageSource.setImageResource(com.example.palette.R.drawable.cnn)
        } else if (item.name == "Fox News" || item.name == "Fox Sports") {
            holder.imageSource.setImageResource(com.example.palette.R.drawable.fox_news)
        } else if (item.name == "CNBC") {
            holder.imageSource.setImageResource(com.example.palette.R.drawable.cnbc)
        } else {
            holder.imageSource.setImageResource(0)
        }

        val country = item.country?.capitalize()
        val category = item.category?.capitalize()
        holder.aboutSource.text = "$category | $country"

        holder.view.setOnClickListener {
            onCLickListenerItem?.invoke(item)
        }

    }
}