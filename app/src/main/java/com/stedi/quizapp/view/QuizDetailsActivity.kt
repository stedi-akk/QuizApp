package com.stedi.quizapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stedi.quizapp.R
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.QuizDetails
import com.stedi.quizapp.other.NoNetworkException
import com.stedi.quizapp.other.showToastLong
import com.stedi.quizapp.vm.QuizDetailsVM
import kotlinx.android.synthetic.main.quiz_list_activity.*
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

      quiz = intent.extras?.getParcelable(KEY_QUIZ_EXTRA) ?: throw IllegalStateException("quiz object is required")

      viewModel = ViewModelProviders.of(this, viewModelFactory)[QuizDetailsVM::class.java].apply {
         quizDetailsLoaded.observe(this@QuizDetailsActivity, Observer { quizDetailsLoaded(it) })
         loadQuizDetailsError.observe(this@QuizDetailsActivity, Observer { loadQuizDetailsError(it) })
      }

      setContentView(R.layout.quiz_details_activity)

      quizDetailsAdapter = QuizDetailsAdapter(this, quiz, { onFinishPressed() }, { onQuizModified() })

      activityRecyclerView.layoutManager = LinearLayoutManager(this)
      activityRecyclerView.adapter = quizDetailsAdapter

      viewModel.loadQuizDetails(quiz)
   }

   private fun quizDetailsLoaded(it: QuizDetails) {
      quizDetailsAdapter.setData(it)
      if (it.questions.isEmpty()) {
         showToastLong(R.string.nothing_to_show)
         finish()
      }
   }

   private fun loadQuizDetailsError(it: Throwable) {
      if (it is NoNetworkException) {
         showToastLong(R.string.no_network_message)
      } else {
         showToastLong(R.string.unknown_error)
      }
      finish()
   }

   private fun onQuizModified() {

   }

   private fun onFinishPressed() {

   }
}