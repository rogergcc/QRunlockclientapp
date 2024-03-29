package com.rogergcc.qrunlockclientapp.presentation.scanner


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class QrScanImage(
    @SerializedName("userId") val userId: String = ""
)