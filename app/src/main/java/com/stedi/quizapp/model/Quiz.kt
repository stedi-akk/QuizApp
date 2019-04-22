package com.stedi.quizapp.model

import com.google.gson.annotations.SerializedName

data class Quiz(
   @SerializedName("questions") val questionsCount: Int? = null,
   @SerializedName("createdAt") val createdAt: String? = null,
   @SerializedName("id") val id: Double? = null,
   @SerializedName("title") val title: String? = null,
   @SerializedName("content") val content: String? = null,
   @SerializedName("mainPhoto") val image: Image? = null
)