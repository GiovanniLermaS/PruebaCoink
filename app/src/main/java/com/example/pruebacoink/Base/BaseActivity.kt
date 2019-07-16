package com.example.pruebacoink.Base

import android.app.AlertDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.example.pruebacoink.Model.User
import com.example.pruebacoink.R
import com.example.pruebacoink.Retrofit.ResultService
import dmax.dialog.SpotsDialog

open class BaseActivity : AppCompatActivity(), ResultService {

    private var dialog: AlertDialog? = null

    fun Context.showProgress(): AlertDialog {
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setCancelable(false)
            .setTheme(R.style.Custom)
            .build()
        dialog?.show()
        return dialog!!
    }

    fun closeProgress() {
        if (dialog?.isShowing!!)
            dialog?.dismiss()
    }

    override fun onDataReturn(user: User?) {}

    override fun onFailedResponse(message: String?) {}
}