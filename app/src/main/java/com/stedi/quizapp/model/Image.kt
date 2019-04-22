package com.stedi.quizapp.model

import com.google.gson.annotations.SerializedName

data class Image(
   @SerializedName("width") val width: String? = null,
   @SerializedName("url") val url: String? = null,
   @SerializedName("height") val height: String? = null
)