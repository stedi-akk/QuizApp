package com.stedi.quizapp.di.modules

import android.app.Application
import com.stedi.quizapp.BuildConfig
import com.stedi.quizapp.model.repository.DatabaseQuizRepository
import com.stedi.quizapp.model.repository.QuizRepository
import com.stedi.quizapp.model.repository.QuizRepositorySourceMapper
import com.stedi.quizapp.model.repository.RestQuizRepository
import com.stedi.quizapp.other.NoNetworkInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class AppModule {

   @Provides
   fun provideOkHttp(application: Application): OkHttpClient {
      return OkHttpClient.Builder().apply {
         connectTimeout(5, TimeUnit.SECONDS)
         readTimeout(5, TimeUnit.SECONDS)
         writeTimeout(5, TimeUnit.SECONDS)
         addInterceptor(NoNetworkInterceptor(application))
         if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
               level = HttpLoggingInterceptor.Level.BODY
            })
         }
      }.build()
   }

   @Provides
   fun provideQuizRepository(application: Application, okHttpClient: OkHttpClient): QuizRepository {
      return QuizRepositorySourceMapper(
         DatabaseQuizRepository(application),
         RestQuizRepository(okHttpClient)
      )
   }
}