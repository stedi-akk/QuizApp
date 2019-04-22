package com.stedi.quizapp.model.repository

import com.google.gson.annotations.SerializedName
import com.stedi.quizapp.model.Quiz
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

class RestQuizRepository(
   private val okHttpClient: OkHttpClient
) : QuizRepository {

   private interface GetCall {
      @GET("quizzes/0/{count}")
      fun call(@Path("count") count: Int): Call<Response>

      class Response(
         @SerializedName("items") val items: List<Quiz> = emptyList()
      )
   }

   override fun get(count: Int): Single<List<Quiz>> {
      return Single.fromCallable {
         val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://quiz.o2.pl/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

         val response = retrofit.create(GetCall::class.java)
            .call(count).execute()

         if (response.isSuccessful) {
            response.body()?.items ?: throw IOException()
         } else {
            throw IOException()
         }
      }
   }
}