package com.stedi.quizapp.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stedi.quizapp.model.Answer
import com.stedi.quizapp.model.Question
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.QuizDetails
import com.stedi.quizapp.model.repository.QuizRepository
import com.stedi.quizapp.other.ioMain
import com.stedi.quizapp.other.toInt
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class QuizDetailsVM @Inject constructor(
   private val quizRepository: QuizRepository
) : ViewModel() {

   val quizDetailsLoaded = MutableLiveData<QuizDetails>()
   val loadQuizDetailsError = MutableLiveData<Throwable>()
   val failedToPickAnswer = MutableLiveData<Triple<Answer, Question, Throwable?>>()

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

   fun pickAnswer(answer: Answer, question: Question) {
      val details = quizDetailsLoaded.value
      if (details == null) {
         failedToPickAnswer.value = Triple(answer, question, null)
         return
      }
      question.answers.find { it.isPicked == true.toInt() }?.isPicked = false.toInt()
      answer.isPicked = true.toInt()
      quizRepository.saveDetails(details)
         .ioMain()
         .subscribe({
            // ignore
         }, {
            it.printStackTrace()
            answer.isPicked = false.toInt()
            failedToPickAnswer.value = Triple(answer, question, it)
         })
         .let {
            disposables.add(it)
         }
   }
}