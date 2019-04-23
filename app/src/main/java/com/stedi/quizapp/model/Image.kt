package com.stedi.quizapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
   @SerializedName("url") val url: String? = null,
   @SerializedName("width") val width: String? = null,
   @SerializedName("height") val height: String? = null
) : Parcelable {

   fun isValid(): Boolean {
      return !url.isNullOrEmpty()
            && !width.isNullOrEmpty()
            && !height.isNullOrEmpty()
   }
}