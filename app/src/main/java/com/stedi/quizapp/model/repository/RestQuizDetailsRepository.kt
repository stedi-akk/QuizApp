package com.stedi.quizapp.model.repository

import com.stedi.quizapp.model.QuizDetails
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

class RestQuizDetailsRepository(
   private val okHttpClient: OkHttpClient
) : QuizDetailsRepository {

   private interface GetCall {
      @GET("quiz/{id}/0")
      fun call(@Path("id") id: Int): Call<QuizDetails>
   }

   override fun get(id: Int): Single<QuizDetails> {
      return Single.fromCallable {
         val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://quiz.o2.pl/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

         val response = retrofit.create(GetCall::class.java)
            .call(id).execute()

         if (response.isSuccessful) {
            response.body() ?: throw IOException()
         } else {
            throw IOException()
         }
      }
   }
}