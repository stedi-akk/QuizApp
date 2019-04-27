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
@DatabaseTable(tableName = "questions")
data class Question(
   @DatabaseField(generatedId = true, columnName = "id")
   var id: Int = 0,

   @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "quizzes_details")
   var quizzesDetails: QuizDetails? = null,

   @SerializedName("image")
   @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "image")
   var image: Image? = null,

   @SerializedName("answers")
   @ForeignCollectionField(eager = true, columnName = "answers")
   var answers: Collection<Answer> = emptyList(),

   @SerializedName("text")
   @DatabaseField(columnName = "text")
   var text: String? = null,

   @SerializedName("order")
   @DatabaseField(columnName = "order")
   var order: Int = 0
) : Parcelable {

   private companion object : Parceler<Question> {
      override fun Question.write(parcel: Parcel, flags: Int) {
         parcel.writeInt(id)
         parcel.writeParcelable(image, flags)
         parcel.writeTypedList(answers.toList())
         parcel.writeString(text)
         parcel.writeInt(order)
      }

      override fun create(parcel: Parcel): Question {
         return Question(
            id = parcel.readInt(),
            image = parcel.readParcelable(Image::class.java.classLoader),
            answers = mutableListOf<Answer>().apply {
               parcel.readTypedList(this, ParcelableCreators.getAnswerCreator())
            },
            text = parcel.readString(),
            order = parcel.readInt()
         )
      }
   }
}