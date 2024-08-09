package com.eurosalud.pdp_cs.ui.login.model

import android.os.Parcel
import android.os.Parcelable

data class DefaultModel(
    val message: String?,
    val status: Int?,

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
        parcel.writeValue(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DefaultModel> {
        override fun createFromParcel(parcel: Parcel): DefaultModel {
            return DefaultModel(parcel)
        }

        override fun newArray(size: Int): Array<DefaultModel?> {
            return arrayOfNulls(size)
        }
    }
}