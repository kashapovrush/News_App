package com.example.features_filter_news.model

data class Day(
    val value: String,
    val isCurrentDay: Int = NOT_TODAY,
    val isEnabled: Int = DISABLED,
    val isBetween: Int = NOT_BETWEEN
) {
    companion object {
        const val TODAY = 0
        const val NOT_TODAY = 1

        const val ENABLED = 1
        const val DISABLED = 0

        const val IS_BETWEEN = 1
        const val NOT_BETWEEN = 0

    }

}
