package com.kashapovrush.api.modelsDto

import com.google.gson.annotations.SerializedName

data class Sources(
    @SerializedName("sources")
    val sources: List<Source>
)
