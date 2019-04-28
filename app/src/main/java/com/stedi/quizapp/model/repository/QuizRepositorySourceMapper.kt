package com.stedi.quizapp.model.repository

import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.QuizDetails
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * To map data from second source to first source.
 * For example, to save data from server to database.
 */
class QuizRepositorySourceMapper(
   private val firstSource: QuizRepository,
   private val secondSource: QuizRepository
) : QuizRepository {

   override fun get(count: Int): Single<List<Quiz>> {
      return firstSource.get(count)
         .flatMap {
            if (it.isEmpty()) {
               secondSource.get(count)
                  .flatMap {
                     Observable.fromIterable(it)
                        .flatMapCompletable { firstSource.save(it) }
                        .andThen(Single.just(it))
                  }
            } else {
               Single.just(it)
            }
         }
   }

   override fun getDetails(quiz: Quiz): Maybe<QuizDetails> {
      return firstSource.getDetails(quiz)
         .switchIfEmpty(secondSource.getDetails(quiz)
            .flatMap {
               Single.just(it)
                  .flatMapCompletable { firstSource.saveDetails(it) }
                  .andThen(Maybe.just(it))
            })
   }

   override fun save(quiz: Quiz): Completable {
      return secondSource.save(quiz).andThen(firstSource.save(quiz))
   }

   override fun saveDetails(quizDetails: QuizDetails): Completable {
      return secondSource.saveDetails(quizDetails).andThen(firstSource.saveDetails(quizDetails))
   }
}