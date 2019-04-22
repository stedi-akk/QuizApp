package com.stedi.quizapp.di

import android.app.Application
import com.stedi.quizapp.App
import com.stedi.quizapp.di.modules.AppModule
import com.stedi.quizapp.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
   modules = [
      AndroidInjectionModule::class,
      AppBindings::class,
      AppModule::class,
      ViewModelModule::class
   ]
)
interface AppInjector : AndroidInjector<App> {

   @Component.Builder
   interface Builder {

      @BindsInstance
      fun application(application: Application): Builder

      fun build(): AppInjector
   }
}