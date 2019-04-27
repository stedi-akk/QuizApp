package com.stedi.quizapp.model;

import android.os.Parcelable;

import androidx.annotation.NonNull;

// https://youtrack.jetbrains.com/issue/KT-19853
@SuppressWarnings("unchecked")
public final class ParcelableCreators {

   @NonNull
   public static Parcelable.Creator<Answer> getAnswerCreator() {
      return Answer.CREATOR;
   }

   @NonNull
   public static Parcelable.Creator<Question> getQuestionCreator() {
      return Question.CREATOR;
   }
}
