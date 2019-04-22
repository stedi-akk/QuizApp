package com.stedi.quizapp.model

import com.google.gson.annotations.SerializedName

data class Answer(
   @SerializedName("order") val order: Int? = null,
   @SerializedName("text") val text: String? = null,
   @SerializedName("isCorrect") val isCorrect: Int? = null,
   var isPicked: Int = 0
)