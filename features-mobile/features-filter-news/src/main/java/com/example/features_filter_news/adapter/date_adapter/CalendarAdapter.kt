package com.example.features_filter_news.adapter.date_adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.features_filter_news.R
import com.example.features_filter_news.model.Day

class CalendarAdapter(val listDays: ArrayList<Day>, val context: Context, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CalendarViewHolder>() {

    var onClickListener: ((Day) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.day_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listDays.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val item = listDays[position]
        holder.day.text = item.value


        if (item.value != "") {
            if (item.isCurrentDay == Day.TODAY) {
                holder.itemView.setBackgroundResource(R.drawable.background_current_day)
                holder.day.setTextColor(context.getColor(com.example.palette.R.color.primary))
            } else if (item.isEnabled == Day.ENABLED) {
                holder.itemView.setBackgroundResource(R.drawable.background_enabled_year)
                holder.day.setTextColor(Color.WHITE)
            } else if (item.isBetween == Day.IS_BETWEEN) {
                holder.itemView.setBackgroundResource(R.drawable.background_between_enabled_days)
            }
        }

        if (item.value != "") {
            holder.day.setOnClickListener {
                onItemClickListener.selectedItem(item)
            }
        }

    }

    interface OnItemClickListener {

        fun selectedItem(day: Day)
    }


}