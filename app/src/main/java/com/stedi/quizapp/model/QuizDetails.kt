package com.stedi.quizapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizDetails(
   @SerializedName("id") val id: String? = null,
   @SerializedName("questions") val questions: List<Question> = emptyList()
) : Parcelable