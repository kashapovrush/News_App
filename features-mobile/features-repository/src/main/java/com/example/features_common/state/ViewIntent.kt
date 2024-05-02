package com.example.features_common.state

sealed class ViewIntent() {

    object RequestData: ViewIntent()

    object UpdateList: ViewIntent()

}
