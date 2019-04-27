package com.stedi.quizapp.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.QuizDetails
import com.stedi.quizapp.model.repository.QuizRepository
import com.stedi.quizapp.other.ioMain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class QuizDetailsVM @Inject constructor(
   private val quizRepository: QuizRepository
) : ViewModel() {

   val quizDetailsLoaded = MutableLiveData<QuizDetails>()
   val loadQuizDetailsError = MutableLiveData<Throwable>()

   private val disposables: CompositeDisposable = CompositeDisposable()

   override fun onCleared() {
      super.onCleared()
      disposables.dispose()
   }

   fun loadQuizDetails(quiz: Quiz) {
      quizRepository.getDetails(quiz)
         .toSingle()
         .ioMain()
         .subscribe({
            quizDetailsLoaded.value = it
         }, {
            it.printStackTrace()
            loadQuizDetailsError.value = it
         })
         .let {
            disposables.add(it)
         }
   }
}