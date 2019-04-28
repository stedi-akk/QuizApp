package com.stedi.quizapp.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stedi.quizapp.RxImmediateSchedulerRule
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.repository.QuizRepository
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class QuizListVMTest {

   companion object {
      @ClassRule
      @JvmField
      val schedulers = RxImmediateSchedulerRule()
   }

   @Rule
   @JvmField
   val rule = InstantTaskExecutorRule()

   @InjectMocks
   lateinit var viewModel: QuizListVM

   @Mock
   lateinit var quizRepository: QuizRepository

   @Before
   fun before() {
      MockitoAnnotations.initMocks(this)
   }

   @Test
   fun testLoadQuizListSuccess() {
      val list = listOf(Quiz(title = "ololo"), Quiz(title = "trololo"))
      given(quizRepository.get(anyInt())).willReturn(Single.just(list))

      viewModel.loadQuizList()

      val result = viewModel.quizListLoaded.value
      assertNotNull(result)
      assertNull(viewModel.loadQuizListError.value)
      assertTrue(result!!.size == 2)
      assertTrue(result[0] == list[0])
      assertTrue(result[1] == list[1])
   }

   @Test
   fun testLoadQuizListEmptySuccess() {
      given(quizRepository.get(anyInt())).willReturn(Single.just(emptyList()))

      viewModel.loadQuizList()

      val result = viewModel.quizListLoaded.value
      assertNotNull(result)
      assertNull(viewModel.loadQuizListError.value)
      assertTrue(result!!.isEmpty())
   }

   @Test
   fun testLoadQuizListError() {
      val error = IllegalStateException("nope")
      given(quizRepository.get(anyInt())).willReturn(Single.error(error))

      viewModel.loadQuizList()

      val result = viewModel.loadQuizListError.value
      assertNull(viewModel.quizListLoaded.value)
      assertNotNull(result)
      assertTrue(result == error)
   }
}