package com.stedi.quizapp.model.repository

import com.google.gson.annotations.SerializedName
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.QuizDetails
import io.reactivex.Completable
import io.reactivex.Maybe
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
      fun call(@Path("count") count: Int): Call<TempResponse>

      class TempResponse(
         @SerializedName("items") val items: List<Quiz> = emptyList()
      )
   }

   override fun get(count: Int): Single<List<Quiz>> {
      return Single.fromCallable {
         val response = makeRetrofit(okHttpClient)
            .create(GetCall::class.java)
            .call(count)
            .execute()

         if (response.isSuccessful) {
            response.body()?.items ?: throw IOException()
         } else {
            throw IOException()
         }
      }
   }

   private interface GetDetailsCall {
      @GET("quiz/{id}/0")
      fun call(@Path("id") id: String): Call<QuizDetails>
   }

   override fun getDetails(quiz: Quiz): Maybe<QuizDetails> {
      return Maybe.fromCallable {
         val response = makeRetrofit(okHttpClient)
            .create(GetDetailsCall::class.java)
            .call(quiz.id!!)
            .execute()

         if (response.isSuccessful) {
            response.body() ?: throw IOException()
         } else {
            throw IOException()
         }
      }
   }

   override fun save(quiz: Quiz) = Completable.complete()

   override fun saveDetails(quizDetails: QuizDetails) = Completable.complete()

   private fun makeRetrofit(okHttpClient: OkHttpClient): Retrofit {
      return Retrofit.Builder()
         .client(okHttpClient)
         .baseUrl("http://quiz.o2.pl/api/v1/")
         .addConverterFactory(GsonConverterFactory.create())
         .build()
   }
}