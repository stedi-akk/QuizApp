package com.stedi.quizapp.model.repository

import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.QuizDetails
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface QuizRepository {

   fun get(count: Int): Single<List<Quiz>>

   fun getDetails(quiz: Quiz): Maybe<QuizDetails>

   fun save(quiz: Quiz): Completable

   fun saveDetails(quizDetails: QuizDetails): Completable
}