package com.kashapovrush.api.modelsDto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("category")
    var category: String?= "",
    @SerializedName("country")
    var country: String? = ""
): Parcelable
