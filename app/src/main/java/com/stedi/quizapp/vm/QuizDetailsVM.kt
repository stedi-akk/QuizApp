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

   val quizDetailsLoaded = MutableLiveData<Pair<Quiz, QuizDetails>>()
   val loadQuizDetailsError = MutableLiveData<Throwable>()
   val pickAnswerError = MutableLiveData<Triple<Answer, Question, Throwable?>>()

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
            quizDetailsLoaded.value = quiz to it
         }, {
            it.printStackTrace()
            loadQuizDetailsError.value = it
         })
         .let {
            disposables.add(it)
         }
   }

   fun pickAnswer(answer: Answer, question: Question) {
      val quiz = quizDetailsLoaded.value?.first
      val details = quizDetailsLoaded.value?.second
      if (quiz == null || details == null) {
         pickAnswerError.value = Triple(answer, question, null)
         return
      }

      // unpick previous answer
      question.answers.find { it.isPicked == true.toInt() }?.isPicked = false.toInt()
      // pick a new one
      answer.isPicked = true.toInt()
      // update answered count in quiz object
      quiz.answeredCount = details.questions.filter { !it.answers.none { it.isPicked == true.toInt() } }.size

      quizRepository.saveDetails(details)
         .andThen(quizRepository.save(quiz))
         .ioMain()
         .subscribe({
            // ignore
         }, {
            it.printStackTrace()
            pickAnswerError.value = Triple(answer, question, it)
         })
         .let {
            disposables.add(it)
         }
   }
}