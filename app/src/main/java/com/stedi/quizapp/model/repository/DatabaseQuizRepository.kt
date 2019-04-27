package com.stedi.quizapp.model.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.stedi.quizapp.model.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.sql.SQLException

class DatabaseQuizRepository(
   context: Context
) : OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), QuizRepository {

   companion object {
      const val DATABASE_NAME = "app_database"
      const val DATABASE_VERSION = 1
   }

   override fun onCreate(database: SQLiteDatabase, connectionSource: ConnectionSource) {
      try {
         arrayOf(Answer::class.java, Image::class.java, Question::class.java, QuizDetails::class.java, Quiz::class.java).forEach {
            TableUtils.createTableIfNotExists(connectionSource, it)
         }
      } catch (e: Exception) {
         e.printStackTrace()
      }
   }

   override fun onUpgrade(database: SQLiteDatabase, connectionSource: ConnectionSource, oldVersion: Int, newVersion: Int) {
   }

   override fun get(count: Int): Single<List<Quiz>> {
      return Single.fromCallable {
         getDao(Quiz::class.java).let {
            it.query(it.queryBuilder().limit(count.toLong()).prepare())
         }
      }
   }

   override fun getDetails(quiz: Quiz): Maybe<QuizDetails> {
      return Maybe.create { emitter ->
         getDao(QuizDetails::class.java).let {
            it.query(it.queryBuilder().where().eq("id", quiz.id).prepare())
         }.firstOrNull()?.let {
            emitter.onSuccess(it)
         } ?: emitter.onComplete()
      }
   }

   override fun save(quiz: Quiz): Completable {
      return Completable.fromCallable {
         verifySaved(getDao(Quiz::class.java).createOrUpdate(quiz))
      }
   }

   override fun saveDetails(quizDetails: QuizDetails): Completable {
      return Completable.fromCallable {
         quizDetails.questions.forEach { question ->
            question.answers.forEach { answer ->
               answer.question = question
               verifySaved(getDao(Answer::class.java).createOrUpdate(answer))
            }
            question.quizzesDetails = quizDetails
            verifySaved(getDao(Question::class.java).createOrUpdate(question))
         }
         verifySaved(getDao(QuizDetails::class.java).createOrUpdate(quizDetails))
      }
   }

   private fun verifySaved(status: Dao.CreateOrUpdateStatus) {
      if (!status.isCreated && !status.isUpdated) {
         throw SQLException("failed to save or update")
      }
   }
}