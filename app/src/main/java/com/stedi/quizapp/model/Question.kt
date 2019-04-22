package com.stedi.quizapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
   @SerializedName("image") val image: Image? = null,
   @SerializedName("answers") val answers: List<Answer> = emptyList(),
   @SerializedName("text") val text: String? = null,
   @SerializedName("order") val order: Int = 0
) : Parcelable