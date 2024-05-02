package com.example.features_common.adapter.filteredNews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.features_common.databinding.ItemErrorBinding
import com.example.features_common.databinding.ItemProgressBinding

class FilteredNewsStateAdapter : LoadStateAdapter<FilteredNewsStateAdapter.ItemViewHolder>() {

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when(loadState) {
            is LoadState.Error -> ErrorViewHolder(LayoutInflater.from(parent.context), parent)
            LoadState.Loading -> ProgressViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.NotLoading -> error("not supported")
        }
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return when(loadState) {
            is LoadState.Error -> ERROR
            LoadState.Loading -> PROGRESS
            is LoadState.NotLoading -> error("not supported")
        }
    }

    abstract class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(loadState: LoadState)
    }

    class ProgressViewHolder constructor(
        private val binding: ItemProgressBinding
    ) : ItemViewHolder(binding.root) {
        override fun bind(loadState: LoadState) {
            // null
        }

        companion object {
            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false
            ): ProgressViewHolder {
                return ProgressViewHolder(
                    ItemProgressBinding.inflate(
                        layoutInflater,
                        parent,
                        attachToRoot
                    )
                )
            }
        }

    }

    class ErrorViewHolder constructor(
        private val binding: ItemErrorBinding
    ) : ItemViewHolder(binding.root) {
        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            binding.errorMessage.text = loadState.error.localizedMessage
        }

        companion object {
            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false
            ): ErrorViewHolder {
                return ErrorViewHolder(
                    ItemErrorBinding.inflate(
                        layoutInflater,
                        parent,
                        attachToRoot
                    )
                )
            }
        }

    }

    companion object {

        const val PROGRESS = 1
        const val ERROR = 0
    }

}