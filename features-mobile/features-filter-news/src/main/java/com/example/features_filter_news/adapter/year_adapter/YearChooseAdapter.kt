package com.example.features_filter_news.adapter.year_adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.features_filter_news.R
import com.example.features_filter_news.model.Year

class YearChooseAdapter(val list: List<Year>, val onItemClickListener: OnItemClickListener) :
    ListAdapter<Year, YearChooseViewHolder>(DiffUtilYearCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): YearChooseViewHolder {

        return YearChooseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.year_disabled_tem,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: YearChooseViewHolder, position: Int) {

        holder.yearCell.text = list[position].value

        holder.yearCell.setOnClickListener {
            if (list[position].isActive == Year.DISABLED) {
                onItemClickListener.setEnabledState(position, list[position])
                holder.yearCell.setTextColor(Color.WHITE)
            }
        }
    }

    interface OnItemClickListener {
        fun setEnabledState(position: Int, year: Year)
    }

}