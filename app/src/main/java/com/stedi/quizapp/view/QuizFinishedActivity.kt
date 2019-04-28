package com.stedi.quizapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.stedi.quizapp.R
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.other.showToastLong
import com.stedi.quizapp.other.toBoolean
import com.stedi.quizapp.vm.QuizFinishVM
import kotlinx.android.synthetic.main.quiz_finished_activity.*
import javax.inject.Inject

class QuizFinishedActivity : BaseActivity() {

   companion object {
      private const val KEY_QUIZ_EXTRA = "KEY_QUIZ_EXTRA"

      fun start(context: Context, quiz: Quiz) {
         context.startActivity(
            Intent(context, QuizFinishedActivity::class.java)
               .putExtra(KEY_QUIZ_EXTRA, quiz)
         )
      }
   }

   @Inject
   lateinit var viewModelFactory: ViewModelProvider.Factory

   private lateinit var quiz: Quiz
   private lateinit var viewModel: QuizFinishVM

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      quiz = intent.extras?.getParcelable(KEY_QUIZ_EXTRA) ?: throw IllegalStateException("quiz object is required")

      viewModel = ViewModelProviders.of(this, viewModelFactory)[QuizFinishVM::class.java].apply {
         quizMarkedAsFinished.observe(this@QuizFinishedActivity, Observer { showResult() })
         markedAsFinishedError.observe(this@QuizFinishedActivity, Observer { onViewModelError(it) })
         quizReverted.observe(this@QuizFinishedActivity, Observer { onQuizReverted() })
         revertQuizError.observe(this@QuizFinishedActivity, Observer { onViewModelError(it) })
      }

      setContentView(R.layout.quiz_finished_activity)

      backToQuizListButton.setOnClickListener { finish() }
      solveQuizAgainButton.setOnClickListener { viewModel.revertQuiz(quiz) }

      if (quiz.isFinished.toBoolean()) {
         showResult()
      } else {
         viewModel.markAsFinished(quiz)
      }
   }

   private fun showResult() {
      yourResult.text = getString(
         R.string.your_result,
         quiz.getPercentageResult().toString(),
         quiz.correctAnsweredCount.toString(),
         quiz.questionsCount.toString()
      )
   }

   private fun onQuizReverted() {
      QuizDetailsActivity.start(this, quiz)
      finish()
   }

   private fun onViewModelError(it: Throwable?) {
      showToastLong(R.string.unknown_error)
      finish()
   }
}