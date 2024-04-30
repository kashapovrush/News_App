package com.example.features_filter_news.ui

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.time.LocalDate

class LocalDateParcelable(val localDate: LocalDate): Parcelable {


    @RequiresApi(Build.VERSION_CODES.O)
    constructor(parcel: Parcel) : this( LocalDate.parse(parcel.readString())) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(localDate.toString())
    }

    companion object CREATOR : Parcelable.Creator<LocalDateParcelable> {

        override fun createFromParcel(parcel: Parcel): LocalDateParcelable {
            return LocalDateParcelable(parcel)
        }

        override fun newArray(size: Int): Array<LocalDateParcelable?> {
            return arrayOfNulls(size)
        }
    }
}