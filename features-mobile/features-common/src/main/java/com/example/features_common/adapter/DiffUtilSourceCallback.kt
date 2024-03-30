package com.example.features_common.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kashapovrush.api.modelsDto.Source

class DiffUtilSourceCallback: DiffUtil.ItemCallback<Source>() {
    override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
        return oldItem == newItem
    }
}