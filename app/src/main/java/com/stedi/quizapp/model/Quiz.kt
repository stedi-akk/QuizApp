package com.stedi.quizapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import kotlinx.android.parcel.Parcelize
import kotlin.math.roundToInt

@Parcelize
@DatabaseTable(tableName = "quizzes")
data class Quiz(
   @SerializedName("id")
   @DatabaseField(id = true, columnName = "id")
   var id: String? = null,

   @SerializedName("questions")
   @DatabaseField(columnName = "questions_count")
   var questionsCount: Int = 0,

   @SerializedName("createdAt")
   @DatabaseField(columnName = "created_at")
   var createdAt: String? = null,

   @SerializedName("title")
   @DatabaseField(columnName = "title")
   var title: String? = null,

   @SerializedName("content")
   @DatabaseField(columnName = "content")
   var content: String? = null,

   @SerializedName("mainPhoto")
   @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "main_photo")
   var image: Image? = null,

   @DatabaseField(columnName = "answered_count")
   var answeredCount: Int = 0
) : Parcelable {

   fun getPercentageProgress(): Int {
      return (100f / questionsCount.toFloat() * answeredCount.toFloat()).roundToInt()
   }
}