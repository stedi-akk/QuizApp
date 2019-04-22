package com.stedi.quizapp.vm

import androidx.lifecycle.ViewModel
import com.stedi.quizapp.model.repository.QuizRepository
import javax.inject.Inject

class QuizListVM @Inject constructor(
   private val quizRepository: QuizRepository
) : ViewModel() {

}