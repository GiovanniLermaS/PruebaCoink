package com.example.pruebacoink.Retrofit

import android.app.AlertDialog
import android.content.Context
import com.example.pruebacoink.Base.BaseActivity
import com.example.pruebacoink.Model.User
import com.example.pruebacoink.R
import com.example.pruebacoink.Utils.Constants
import com.example.pruebacoink.Utils.getAPIService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ClientNet {
    companion object {
        private lateinit var r: Retrofit
        fun getClient(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            r = Retrofit.Builder()
                .baseUrl(Constants.Connection.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return r
        }
    }
}

fun Context.requestService(
    request: Any?,
    method: String,
    url: String,
    showProgress: Boolean
) {
    val result: ResultService = this@requestService as ResultService
    var dialog: AlertDialog? = null
    if (showProgress) dialog = (this@requestService as BaseActivity).showProgress()
    val callback: Subscriber<Any> = object : Subscriber<Any>() {
        override fun onNext(t: Any) {
            if (showProgress) dialog?.dismiss()
            val user = Gson().fromJson(Gson().toJson(t), User::class.java) as User
            result.onDataReturn(user)
        }

        override fun onCompleted() {
        }

        override fun onError(e: Throwable?) {
            if (showProgress) dialog?.dismiss()
            result.onFailedResponse(getString(R.string.datesWrongType))
        }
    }

    when (method) {
        Constants.HttpMethodsType.POST ->
            getAPIService().requestServicePost(url, request!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback)
    }
}