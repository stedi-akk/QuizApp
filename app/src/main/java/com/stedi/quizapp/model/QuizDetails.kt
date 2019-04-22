package com.stedi.quizapp.model

import com.google.gson.annotations.SerializedName

data class QuizDetails(
   @SerializedName("id") val id: Double? = null,
   @SerializedName("questions") val questions: List<Question>? = null
)