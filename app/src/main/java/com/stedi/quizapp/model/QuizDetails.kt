package com.stedi.quizapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.field.ForeignCollectionField
import com.j256.ormlite.table.DatabaseTable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
@DatabaseTable(tableName = "quizzes_details")
data class QuizDetails(
   @SerializedName("id")
   @DatabaseField(id = true, columnName = "id")
   var id: String? = null,

   @SerializedName("questions")
   @ForeignCollectionField(eager = true, maxEagerLevel = 2, columnName = "questions")
   var questions: Collection<Question> = emptyList()
) : Parcelable {

   private companion object : Parceler<QuizDetails> {
      override fun QuizDetails.write(parcel: Parcel, flags: Int) {
         parcel.writeString(id)
         parcel.writeTypedList(questions.toList())
      }

      override fun create(parcel: Parcel): QuizDetails {
         return QuizDetails(
            id = parcel.readString(),
            questions = mutableListOf<Question>().apply {
               parcel.readTypedList(this, ParcelableCreators.getQuestionCreator())
            }
         )
      }
   }
}