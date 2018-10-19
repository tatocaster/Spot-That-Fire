package me.tatocaster.nasaappchallenge.entity

import android.os.Parcel
import android.os.Parcelable

data class WildFireActivity(private val name: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    constructor() : this("")

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(name)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<WildFireActivity> {
        override fun createFromParcel(parcel: Parcel): WildFireActivity {
            return WildFireActivity(parcel)
        }

        override fun newArray(size: Int): Array<WildFireActivity?> {
            return arrayOfNulls(size)
        }
    }
}