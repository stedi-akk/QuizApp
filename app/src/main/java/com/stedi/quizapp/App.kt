package com.stedi.quizapp

import com.stedi.quizapp.di.DaggerAppInjector
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

   override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
      return DaggerAppInjector.builder().application(this).build()
   }
}