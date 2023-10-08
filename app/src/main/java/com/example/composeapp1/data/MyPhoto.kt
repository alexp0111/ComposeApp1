package com.example.composeapp1.data

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class MyPhoto(
    var time: String = "",
    var date: String = "",
) : Parcelable, Serializable