package com.stedi.quizapp

import androidx.appcompat.app.AppCompatDelegate
import com.stedi.quizapp.di.DaggerAppInjector
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

   override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
      return DaggerAppInjector.builder().application(this).build()
   }

   override fun onCreate() {
      super.onCreate()
      AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
   }
}