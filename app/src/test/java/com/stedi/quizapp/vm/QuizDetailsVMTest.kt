package com.stedi.quizapp.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stedi.quizapp.RxImmediateSchedulerRule
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.QuizDetails
import com.stedi.quizapp.model.repository.QuizRepository
import io.reactivex.Maybe
import org.junit.Assert.*
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class QuizDetailsVMTest {

   companion object {
      @ClassRule
      @JvmField
      val schedulers = RxImmediateSchedulerRule()
   }

   @Rule
   @JvmField
   val rule = InstantTaskExecutorRule()

   @InjectMocks
   lateinit var viewModel: QuizDetailsVM

   @Mock
   lateinit var quizRepository: QuizRepository

   @Before
   fun before() {
      MockitoAnnotations.initMocks(this)
   }

   @Test
   fun testLoadQuizDetailsSuccess() {
      val quiz = Quiz()
      val details = QuizDetails()
      given(quizRepository.getDetails(quiz)).willReturn(Maybe.just(details))

      viewModel.loadQuizDetails(quiz)

      val result = viewModel.quizDetailsLoaded.value
      assertNotNull(result)
      assertNull(viewModel.loadQuizDetailsError.value)
      assertTrue(result!!.first == quiz)
      assertTrue(result.second == details)
   }

   @Test
   fun testLoadQuizDetailsNull() {
      val quiz = Quiz()
      given(quizRepository.getDetails(quiz)).willReturn(Maybe.empty())

      viewModel.loadQuizDetails(quiz)

      val result = viewModel.loadQuizDetailsError.value
      assertNull(viewModel.quizDetailsLoaded.value)
      assertNotNull(result)
      assertTrue(result is NoSuchElementException)
   }

   @Test
   fun testLoadQuizDetailsError() {
      val quiz = Quiz()
      val error = IllegalStateException("try harder next time")
      given(quizRepository.getDetails(quiz)).willReturn(Maybe.error(error))

      viewModel.loadQuizDetails(quiz)

      val result = viewModel.loadQuizDetailsError.value
      assertNull(viewModel.quizDetailsLoaded.value)
      assertNotNull(result)
      assertTrue(result == error)
   }

   @Test
   fun testPickAnswer() {

   }
}