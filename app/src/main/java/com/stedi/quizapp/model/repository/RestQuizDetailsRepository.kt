package com.stedi.quizapp.model.repository

import com.stedi.quizapp.model.QuizDetails
import io.reactivex.Single
import okhttp3.OkHttpClient

class RestQuizDetailsRepository(
   private val okHttpClient: OkHttpClient
) : QuizDetailsRepository {

   override fun get(id: Int): Single<QuizDetails> {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
   }
}