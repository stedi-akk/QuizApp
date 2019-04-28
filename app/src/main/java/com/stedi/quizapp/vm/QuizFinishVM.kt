package com.stedi.quizapp.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.repository.QuizRepository
import com.stedi.quizapp.other.ioMain
import com.stedi.quizapp.other.toInt
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class QuizFinishVM @Inject constructor(
   private val quizRepository: QuizRepository
) : ViewModel() {

   val quizMarkedAsFinished = MutableLiveData<Quiz>()
   val markedAsFinishedError = MutableLiveData<Throwable>()
   val quizReverted = MutableLiveData<Quiz>()
   val revertQuizError = MutableLiveData<Throwable>()

   private val disposables: CompositeDisposable = CompositeDisposable()

   override fun onCleared() {
      super.onCleared()
      disposables.dispose()
   }

   fun markAsFinished(quiz: Quiz) {
      quizRepository.getDetails(quiz)
         .flatMapCompletable {
            quiz.isFinished = true.toInt()
            quiz.correctAnsweredCount = it.questions.filter {
               !it.answers.none { it.isPicked == true.toInt() && it.isCorrect == true.toInt() }
            }.size
            quizRepository.save(quiz)
         }
         .ioMain()
         .subscribe({
            quizMarkedAsFinished.value = quiz
         }, {
            it.printStackTrace()
            markedAsFinishedError.value = it
         })
         .let {
            disposables.add(it)
         }
   }

   fun revertQuiz(quiz: Quiz) {
      quizRepository.getDetails(quiz)
         .flatMapCompletable {
            it.questions.forEach {
               it.answers.forEach { it.isPicked = false.toInt() }
            }
            quiz.answeredCount = 0
            quiz.correctAnsweredCount = 0
            quiz.isFinished = false.toInt()
            quizRepository.saveDetails(it)
               .andThen(quizRepository.save(quiz))
         }
         .ioMain()
         .subscribe({
            quizReverted.value = quiz
         }, {
            it.printStackTrace()
            revertQuizError.value = it
         })
         .let {
            disposables.add(it)
         }
   }
}