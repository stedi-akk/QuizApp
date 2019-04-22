package com.stedi.quizapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Answer(
   @SerializedName("order") val order: Int = 0,
   @SerializedName("text") val text: String? = null,
   @SerializedName("isCorrect") val isCorrect: Int = 0,
   var isPicked: Int = 0
) : Parcelable