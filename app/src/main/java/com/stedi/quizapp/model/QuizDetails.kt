package com.stedi.quizapp.model

import com.google.gson.annotations.SerializedName

data class QuizDetails(
   @SerializedName("id") val id: Int? = null,
   @SerializedName("questions") val questions: List<Question>? = null
)