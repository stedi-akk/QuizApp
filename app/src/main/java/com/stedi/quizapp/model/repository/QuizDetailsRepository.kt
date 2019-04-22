package com.stedi.quizapp.model.repository

import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.QuizDetails
import io.reactivex.Single

interface QuizDetailsRepository {

   fun get(quiz: Quiz): Single<QuizDetails>
}