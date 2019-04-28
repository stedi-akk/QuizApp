package com.stedi.quizapp

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor

/**
 * A JUnit Test Rule that swaps the background executor used by RxJava and RxAndroid with a
 * different one which executes each task synchronously.
 */
class RxImmediateSchedulerRule : TestRule {
   private val immediate = object : Scheduler() {
      override fun createWorker(): Worker {
         return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
      }
   }

   override fun apply(base: Statement, description: Description): Statement {
      return object : Statement() {
         @Throws(Throwable::class)
         override fun evaluate() {
            RxJavaPlugins.setInitIoSchedulerHandler { immediate }
            RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
            RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
            try {
               base.evaluate()
            } finally {
               RxJavaPlugins.reset()
               RxAndroidPlugins.reset()
            }
         }
      }
   }
}