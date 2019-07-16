package com.example.pruebacoink.Retrofit

import retrofit2.http.*
import rx.Observable
import retrofit2.http.Url
import retrofit2.http.POST

interface ApiInterface {

    @POST
    fun requestServicePost(@Url url: String, @Body request: Any): Observable<Any>
}