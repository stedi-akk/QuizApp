package com.stedi.quizapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quiz(
   @SerializedName("questions") val questionsCount: Int = 0,
   @SerializedName("createdAt") val createdAt: String? = null,
   @SerializedName("id") val id: String? = null,
   @SerializedName("title") val title: String? = null,
   @SerializedName("content") val content: String? = null,
   @SerializedName("mainPhoto") val image: Image? = null
) : Parcelable