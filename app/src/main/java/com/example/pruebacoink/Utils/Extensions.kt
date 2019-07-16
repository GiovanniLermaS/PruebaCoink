package com.example.pruebacoink.Utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.pruebacoink.R
import com.example.pruebacoink.Retrofit.ApiInterface
import com.example.pruebacoink.Retrofit.ClientNet

fun View.setWarningsRequest(): Boolean {
    return if (this is ViewGroup) {
        this.evaluateWarningsView()
    } else false
}

fun ViewGroup.evaluateWarningsView(): Boolean {
    val result = false
    this.invalidate()
    (0 until this.childCount)
        .takeWhile { this.getChildAt(it).visibility != View.GONE }
        .forEach {
            if (this.getChildAt(it) is EditText) {
                if ((this.getChildAt(it) as EditText).setRequestWarning(this.context)) return true
            } else if (this.getChildAt(it) is ViewGroup) {
                if (this.getChildAt(it).setWarningsRequest()) return true
            }
        }
    return result
}

fun EditText.setRequestWarning(context: Context): Boolean {
    return this.setWarning(context.getString(R.string.requiredField), context)
}

fun EditText.setWarning(message: String, context: Context): Boolean {
    return if (this.text.isNotEmpty()) false
    else {
        this.requestFocus()
        val icWarnings = ContextCompat.getDrawable(context, R.drawable.ic_warning)
        icWarnings!!.setBounds(0, 0, icWarnings.intrinsicWidth, icWarnings.intrinsicHeight)
        this.setError(message, icWarnings)
        true
    }
}

fun getAPIService(): ApiInterface {
    return ClientNet.getClient().create(ApiInterface::class.java)
}