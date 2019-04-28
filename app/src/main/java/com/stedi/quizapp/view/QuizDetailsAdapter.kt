package com.stedi.quizapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stedi.quizapp.R
import com.stedi.quizapp.model.Answer
import com.stedi.quizapp.model.Question
import com.stedi.quizapp.model.Quiz
import com.stedi.quizapp.model.QuizDetails
import com.stedi.quizapp.other.toBoolean
import com.stedi.quizapp.other.visibleOrGone
import kotlinx.android.synthetic.main.question_item.view.*
import kotlinx.android.synthetic.main.question_item_footer.view.*
import kotlinx.android.synthetic.main.quiz_content.view.*

class QuizDetailsHolder(item: View) : RecyclerView.ViewHolder(item)

class QuizDetailsAdapter(
   private val context: Context,
   private val quiz: Quiz,
   private val onAnswerPicked: (Answer, Question) -> Unit,
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
   private var finishButtonEnabled = false

   fun setData(quizDetails: QuizDetails?) {
      this.quizDetails = quizDetails ?: EMPTY_DETAILS
      notifyDataSetChanged()
   }

   fun setFinishButtonEnabled(it: Boolean) {
      finishButtonEnabled = it
      notifyItemChanged(itemCount - 1)
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
         ViewType.HEADER.ordinal -> onBindHeader(holder, quiz)
         ViewType.FOOTER.ordinal -> onBindFooter(holder)
         else -> onBindQuestion(holder, quizDetails.questions.elementAt(position - 1))
      }
   }

   private fun onBindHeader(holder: QuizDetailsHolder, quiz: Quiz) {
      holder.itemView.apply {
         if (quiz.image != null && quiz.image!!.isValid()) {
            quizImage.visibleOrGone = true
            Picasso.get()
               .load(quiz.image!!.url)
               .fit()
               .centerInside()
               .into(quizImage)
         } else {
            quizImage.visibleOrGone = false
         }
         quizDate.text = quiz.createdAt ?: TEXT_NOT_FOUND
         quizTitle.visibleOrGone = !quiz.title.isNullOrEmpty()
         quizContent.visibleOrGone = !quiz.content.isNullOrEmpty()
         quizTitle.text = quiz.title ?: TEXT_NOT_FOUND
         quizContent.text = quiz.content ?: TEXT_NOT_FOUND
      }
   }

   private fun onBindFooter(holder: QuizDetailsHolder) {
      holder.itemView.apply {
         finishButton.isEnabled = finishButtonEnabled
         finishButton.setOnClickListener {
            onFinishPressed()
         }
      }
   }

   private fun onBindQuestion(holder: QuizDetailsHolder, question: Question) {
      holder.itemView.apply {
         if (question.image != null && question.image!!.isValid()) {
            questionImage.visibleOrGone = true
            Picasso.get()
               .load(question.image!!.url)
               .fit()
               .centerInside()
               .into(questionImage)
         } else {
            questionImage.visibleOrGone = false
         }
         questionTitle.text = question.text ?: TEXT_NOT_FOUND
         questionsRadioGroup.setOnCheckedChangeListener(null)
         questionsRadioGroup.removeAllViews()
         question.answers.forEachIndexed { index, answer ->
            val rb = AppCompatRadioButton(context).apply {
               id = index
               text = answer.text ?: TEXT_NOT_FOUND
               isChecked = answer.isPicked.toBoolean()
            }
            questionsRadioGroup.addView(rb)
         }
         questionsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            onAnswerPicked(question.answers.elementAt(checkedId), question)
         }
      }
   }
}