package com.stedi.quizapp.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.repository.QuizRepository
import com.stedi.quizapp.other.ioMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class QuizListVM @Inject constructor(
   private val quizRepository: QuizRepository
) : ViewModel() {

   companion object {
      const val DEFAULT_COUNT = 100
   }

   val quizListLoaded = MutableLiveData<List<Quiz>>()
   val loadQuizListError = MutableLiveData<Throwable>()

   private val disposables: CompositeDisposable = CompositeDisposable()

   override fun onCleared() {
      super.onCleared()
      disposables.dispose()
   }

   fun loadQuizList() {
      quizRepository.get(DEFAULT_COUNT)
         .ioMain()
         .subscribe({
            quizListLoaded.value = it
         }, {
            it.printStackTrace()
            loadQuizListError.value = it
         })
         .let {
            disposables.add(it)
         }
   }
}