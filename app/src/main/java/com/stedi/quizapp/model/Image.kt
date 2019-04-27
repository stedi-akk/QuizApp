package com.stedi.quizapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import kotlinx.android.parcel.Parcelize

@Parcelize
@DatabaseTable(tableName = "images")
data class Image(
   @DatabaseField(generatedId = true, columnName = "id")
   var id: Int = 0,

   @SerializedName("url")
   @DatabaseField(columnName = "url")
   var url: String? = null,

   @SerializedName("width")
   @DatabaseField(columnName = "width")
   var width: String? = null,

   @SerializedName("height")
   @DatabaseField(columnName = "height")
   var height: String? = null
) : Parcelable {

   fun isValid(): Boolean {
      return !url.isNullOrEmpty()
            && !width.isNullOrEmpty()
            && !height.isNullOrEmpty()
   }
}