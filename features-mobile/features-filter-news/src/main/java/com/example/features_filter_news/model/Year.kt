package com.example.features_filter_news.model

data class Year(
    val value: String,
    var isActive: Int = DISABLED
) {


    companion object {

        const val ENABLED = 1
        const val DISABLED = 0
    }
}