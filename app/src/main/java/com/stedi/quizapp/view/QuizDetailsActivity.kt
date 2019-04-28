package com.stedi.quizapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stedi.quizapp.R
import com.stedi.quizapp.model.Answer
import com.stedi.quizapp.model.Question
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.QuizDetails
import com.stedi.quizapp.other.NoNetworkException
import com.stedi.quizapp.other.showToastLong
import com.stedi.quizapp.vm.QuizDetailsVM
import kotlinx.android.synthetic.main.quiz_details_activity.*
import javax.inject.Inject

class QuizDetailsActivity : BaseActivity() {

   companion object {
      private const val KEY_QUIZ_EXTRA = "KEY_QUIZ_EXTRA"

      fun start(context: Context, quiz: Quiz) {
         context.startActivity(
            Intent(context, QuizDetailsActivity::class.java)
               .putExtra(KEY_QUIZ_EXTRA, quiz)
         )
      }
   }

   @Inject
   lateinit var viewModelFactory: ViewModelProvider.Factory

   private lateinit var quiz: Quiz
   private lateinit var viewModel: QuizDetailsVM
   private lateinit var quizDetailsAdapter: QuizDetailsAdapter

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      quiz = savedInstanceState?.getParcelable<Quiz>(KEY_QUIZ_EXTRA)
         ?: intent.extras?.getParcelable(KEY_QUIZ_EXTRA)
               ?: throw IllegalStateException("quiz object is required")

      viewModel = ViewModelProviders.of(this, viewModelFactory)[QuizDetailsVM::class.java].apply {
         quizDetailsLoaded.observe(this@QuizDetailsActivity, Observer { quizDetailsLoaded(it.second) })
         loadQuizDetailsError.observe(this@QuizDetailsActivity, Observer { onViewModelError(it) })
         pickAnswerError.observe(this@QuizDetailsActivity, Observer { onViewModelError(it.third) })
      }

      setContentView(R.layout.quiz_details_activity)

      quizDetailsAdapter = QuizDetailsAdapter(this, quiz,
         { it1, it2 -> onAnswerPicked(it1, it2) }, { onFinishPressed() })
      activityRecyclerView.layoutManager = LinearLayoutManager(this)
      activityRecyclerView.adapter = quizDetailsAdapter
      invalidateProgress()

      viewModel.loadQuizDetails(quiz)
   }

   override fun onSaveInstanceState(outState: Bundle) {
      super.onSaveInstanceState(outState)
      outState.putParcelable(KEY_QUIZ_EXTRA, quiz)
   }

   private fun quizDetailsLoaded(it: QuizDetails) {
      quizDetailsAdapter.setData(it)
      if (it.questions.isEmpty()) {
         showToastLong(R.string.nothing_to_show)
         finish()
      }
   }

   private fun onViewModelError(it: Throwable?) {
      if (it is NoNetworkException) {
         showToastLong(R.string.no_network_message)
      } else {
         showToastLong(R.string.unknown_error)
      }
      finish()
   }

   private fun onAnswerPicked(answer: Answer, question: Question) {
      viewModel.pickAnswer(answer, question)
      invalidateProgress()
   }

   private fun onFinishPressed() {
      QuizFinishedActivity.start(this, quiz)
      finish()
   }

   private fun invalidateProgress() {
      quizProgressTitle.text = getString(R.string.quiz_progress_title, quiz.answeredCount.toString(), quiz.questionsCount.toString())
      quizProgress.max = quiz.questionsCount
      quizProgress.progress = quiz.answeredCount
      quizDetailsAdapter.setFinishButtonEnabled(quiz.answeredCount == quiz.questionsCount)
   }
}