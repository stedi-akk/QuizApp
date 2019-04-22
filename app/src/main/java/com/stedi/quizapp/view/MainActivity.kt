package com.stedi.quizapp.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stedi.quizapp.R
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.other.NoNetworkException
import com.stedi.quizapp.other.showToastLong
import com.stedi.quizapp.vm.QuizListVM
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

   @Inject
   lateinit var viewModelFactory: ViewModelProvider.Factory

   private lateinit var viewModel: QuizListVM

   private lateinit var quizListAdapter: QuizListAdapter

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      viewModel = ViewModelProviders.of(this, viewModelFactory)[QuizListVM::class.java].apply {
         quizListLoaded.observe(this@MainActivity, Observer { quizListLoaded(it) })
         loadQuizListError.observe(this@MainActivity, Observer { loadQuizListError(it) })
      }

      setContentView(R.layout.main_activity)

      quizListAdapter = QuizListAdapter(this) { openQuiz(it) }

      activityRecyclerView.layoutManager = LinearLayoutManager(this)
      activityRecyclerView.adapter = quizListAdapter
   }

   override fun onStart() {
      super.onStart()
      if (quizListAdapter.itemCount == 0) {
         viewModel.loadQuizList()
      }
   }

   private fun quizListLoaded(it: List<Quiz>) {
      quizListAdapter.setData(it)
      if (it.isEmpty()) {
         showToastLong(R.string.nothing_to_show)
      }
   }

   private fun loadQuizListError(it: Throwable) {
      if (it is NoNetworkException) {
         showToastLong(R.string.no_network_message)
      } else {
         showToastLong(R.string.unknown_error)
      }
   }

   private fun openQuiz(quiz: Quiz) {

   }
}
