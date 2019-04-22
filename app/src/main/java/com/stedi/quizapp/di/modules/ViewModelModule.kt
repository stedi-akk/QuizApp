package com.stedi.quizapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stedi.quizapp.di.vm.ViewModelFactory
import com.stedi.quizapp.di.vm.ViewModelKey
import com.stedi.quizapp.vm.QuizListVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

   @Binds
   abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

   @Binds
   @IntoMap
   @ViewModelKey(QuizListVM::class)
   abstract fun quizListVM(viewModel: QuizListVM): ViewModel
}