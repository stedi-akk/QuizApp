package com.stedi.quizapp.model

import com.google.gson.annotations.SerializedName

data class Question(
   @SerializedName("image") val image: Image? = null,
   @SerializedName("answers") val answers: List<Answer>? = null,
   @SerializedName("text") val text: String? = null,
   @SerializedName("order") val order: Int? = null
)