package com.stedi.quizapp.other

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class NoNetworkException : Exception()

class NoNetworkInterceptor(private val context: Context) : Interceptor {

   override fun intercept(chain: Interceptor.Chain): Response {
      if (!context.hasNetworkConnection()) {
         throw NoNetworkException()
      }
      return chain.proceed(chain.request())
   }
}