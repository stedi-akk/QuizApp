package com.stedi.quizapp.di

import com.stedi.quizapp.di.modules.AppModule
import com.stedi.quizapp.view.QuizDetailsActivity
import com.stedi.quizapp.view.QuizListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindings {

   @ContributesAndroidInjector(modules = [AppModule::class])
   abstract fun quizListActivity(): QuizListActivity

   @ContributesAndroidInjector(modules = [AppModule::class])
   abstract fun quizDetailsActivity(): QuizDetailsActivity
}