package com.stedi.quizapp.model.repository

import com.stedi.quizapp.model.Quiz
import io.reactivex.Single

interface QuizRepository {

   fun get(count: Int): Single<Quiz>
}