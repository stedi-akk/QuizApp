package com.stedi.quizapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import kotlinx.android.parcel.Parcelize

@Parcelize
@DatabaseTable(tableName = "answers")
data class Answer(
   @DatabaseField(generatedId = true, columnName = "id")
   var id: Int = 0,

   @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "question")
   var question: Question? = null,

   @SerializedName("order")
   @DatabaseField(columnName = "order")
   var order: Int = 0,

   @SerializedName("text")
   @DatabaseField(columnName = "text")
   var text: String? = null,

   @SerializedName("isCorrect")
   @DatabaseField(columnName = "is_correct")
   var isCorrect: Int = 0,

   @DatabaseField(columnName = "is_picked")
   var isPicked: Int = 0
) : Parcelable