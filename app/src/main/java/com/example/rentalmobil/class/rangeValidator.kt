package com.example.rentalmobil.`class`

import android.os.Parcel
import android.os.Parcelable
import com.google.android.material.datepicker.CalendarConstraints

class RangeValidator(
    private val minDate:Long
): CalendarConstraints.DateValidator {
    constructor(parcel: Parcel) : this(parcel.readLong()) {
        parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        TODO("not Implement")
    }

    override fun isValid(date: Long): Boolean {
        return minDate <= date
    }

    override fun describeContents(): Int {
        TODO("not Implement")
    }

    companion object CREATOR : Parcelable.Creator<RangeValidator> {
        override fun createFromParcel(parcel: Parcel): RangeValidator {
            return RangeValidator(parcel)
        }

        override fun newArray(size: Int): Array<RangeValidator?> {
            return arrayOfNulls(size)
        }
    }
}