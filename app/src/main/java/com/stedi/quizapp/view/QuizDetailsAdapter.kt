package com.stedi.quizapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stedi.quizapp.R
import com.stedi.quizapp.model.Question
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.QuizDetails
import kotlinx.android.synthetic.main.question_item.view.*
import kotlinx.android.synthetic.main.question_item_footer.view.*
import kotlinx.android.synthetic.main.quiz_content.view.*

class QuizDetailsHolder(item: View) : RecyclerView.ViewHolder(item)

class QuizDetailsAdapter(
   private val context: Context,
   private val quiz: Quiz,
   private val onQuizModified: () -> Unit,
   private val onFinishPressed: () -> Unit
) : RecyclerView.Adapter<QuizDetailsHolder>() {

   companion object {
      private const val TEXT_NOT_FOUND = "???"
      private val EMPTY_DETAILS = QuizDetails("", emptyList())
   }

   private enum class ViewType {
      HEADER,
      QUESTION,
      FOOTER
   }

   private var quizDetails = EMPTY_DETAILS

   fun setData(quizDetails: QuizDetails?) {
      this.quizDetails = quizDetails ?: EMPTY_DETAILS
      notifyDataSetChanged()
   }

   override fun getItemCount(): Int {
      return if (quizDetails.questions.isEmpty()) {
         1
      } else {
         1 + quizDetails.questions.size + 1
      }
   }

   override fun getItemViewType(position: Int): Int {
      return if (position == 0) {
         ViewType.HEADER.ordinal
      } else if (position > 0 && position == itemCount - 1) {
         ViewType.FOOTER.ordinal
      } else {
         ViewType.QUESTION.ordinal
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizDetailsHolder {
      val layout = when (viewType) {
         ViewType.HEADER.ordinal -> R.layout.question_item_header
         ViewType.FOOTER.ordinal -> R.layout.question_item_footer
         else -> R.layout.question_item
      }
      return QuizDetailsHolder(LayoutInflater.from(context).inflate(layout, parent, false))
   }

   override fun onBindViewHolder(holder: QuizDetailsHolder, position: Int) {
      when (getItemViewType(position)) {
         ViewType.HEADER.ordinal -> onBindHeader(holder)
         ViewType.FOOTER.ordinal -> onBindFooter(holder)
         else -> onBindQuestion(holder, quizDetails.questions[position - 1])
      }
   }

   private fun onBindHeader(holder: QuizDetailsHolder) {
      holder.itemView.apply {
         Picasso.get()
            .load(quiz.image?.url)
            .fit()
            .into(quizImage)
         quizDate.text = quiz.createdAt ?: TEXT_NOT_FOUND
         quizTitle.text = quiz.title ?: TEXT_NOT_FOUND
         quizContent.text = quiz.content ?: TEXT_NOT_FOUND
      }
   }

   private fun onBindFooter(holder: QuizDetailsHolder) {
      holder.itemView.apply {
         finishButton.setOnClickListener {
            onFinishPressed()
         }
      }
   }

   private fun onBindQuestion(holder: QuizDetailsHolder, question: Question) {
      holder.itemView.apply {
         questionTitle.text = question.text ?: TEXT_NOT_FOUND
         questionsRadioGroup.removeAllViews()
         question.answers.forEachIndexed { index, answer ->
            val rb = AppCompatRadioButton(context).apply {
               id = index
               text = answer.text ?: TEXT_NOT_FOUND
            }
            questionsRadioGroup.addView(rb)
         }
         questionsRadioGroup.setOnCheckedChangeListener { _, _ ->
            onQuizModified()
         }
      }
   }
}