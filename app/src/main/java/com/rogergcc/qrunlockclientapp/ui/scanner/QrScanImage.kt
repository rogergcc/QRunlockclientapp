package com.rogergcc.qrunlockclientapp.ui.scanner


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class QrScanImage(
    @SerializedName("userId") val userId: String? = ""
)