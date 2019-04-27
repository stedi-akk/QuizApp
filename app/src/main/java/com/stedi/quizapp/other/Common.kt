package com.stedi.quizapp.other

import android.content.Context
import android.net.ConnectivityManager
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import com.stedi.quizapp.App

fun Context.getApp() = applicationContext as App

fun Context.hasNetworkConnection(): Boolean {
   val cm = getSystemService(Context.CONNECTIVITY_SERVICE) ?: return false
   if (cm is ConnectivityManager) {
      val activeNetwork = cm.activeNetworkInfo ?: return false
      return activeNetwork.isConnectedOrConnecting
   } else {
      return false
   }
}

fun Context.dim2px(@DimenRes id: Int): Int {
   return resources.getDimensionPixelOffset(id)
}

fun Context.dp2px(dp: Float): Float {
   return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

fun Context.showToastLong(@StringRes id: Int) {
   Toast.makeText(this, id, Toast.LENGTH_LONG).show()
}

fun Context.showToastShort(@StringRes id: Int) {
   Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
}

fun Context.showToastLong(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showToastShort(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

var View.visible: Boolean
   get() = visibility == View.VISIBLE
   set(value) {
      visibility = if (value) {
         View.VISIBLE
      } else {
         View.INVISIBLE
      }
   }

var View.visibleOrGone: Boolean
   get() = visibility == View.VISIBLE
   set(value) {
      visibility = if (value) {
         View.VISIBLE
      } else {
         View.GONE
      }
   }

fun Int.toBoolean() = this == 1

fun Boolean.toInt() = if (this) 1 else 0