package com.hvacpro.faultcodes.tuan9.bai1
import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Note(val title: String, val content: String, val date: String) : Parcelable
