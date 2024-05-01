package com.example.features_common.adapter.headlines

import androidx.recyclerview.widget.DiffUtil
import com.example.features_common.models.Source
import com.kashapovrush.api.modelsDto.SourceDto


class DiffUtilSourceCallback: DiffUtil.ItemCallback<Source>() {
    override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
        return oldItem == newItem
    }
}