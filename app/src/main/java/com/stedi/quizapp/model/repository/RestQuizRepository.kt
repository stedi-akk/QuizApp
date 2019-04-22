package com.stedi.quizapp.model.repository

import com.stedi.quizapp.model.Quiz
import io.reactivex.Single
import okhttp3.OkHttpClient

class RestQuizRepository(
   private val okHttpClient: OkHttpClient
) : QuizRepository {

   override fun get(count: Int): Single<Quiz> {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
   }
}