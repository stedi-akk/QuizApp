package com.stedi.quizapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stedi.quizapp.R
import com.stedi.quizapp.model.Quiz
import kotlinx.android.synthetic.main.quiz_item.view.*

class QuizHolder(item: View) : RecyclerView.ViewHolder(item)

class QuizListAdapter(
   private val context: Context,
   private val clickListener: (Quiz) -> Unit
) : RecyclerView.Adapter<QuizHolder>() {

   companion object {
      private const val TEXT_NOT_FOUND = "???"
   }

   private val quizList = ArrayList<Quiz>()

   fun setData(quizList: List<Quiz>) {
      this.quizList.clear()
      this.quizList.addAll(quizList)
      notifyDataSetChanged()
   }

   override fun getItemCount() = quizList.size

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizHolder {
      return QuizHolder(LayoutInflater.from(context).inflate(R.layout.quiz_item, parent, false))
   }

   override fun onBindViewHolder(holder: QuizHolder, position: Int) {
      holder.itemView.apply {
         val quiz = quizList[position]

         Picasso.get()
            .load(quiz.image?.url)
            .fit()
            .into(quizImage)

         quizDate.text = quiz.createdAt ?: TEXT_NOT_FOUND
         quizTitle.text = quiz.title ?: TEXT_NOT_FOUND
         quizContent.text = quiz.content ?: TEXT_NOT_FOUND
         setOnClickListener { clickListener(quiz) }
      }
   }
}