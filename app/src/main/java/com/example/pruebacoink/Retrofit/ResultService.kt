package com.example.pruebacoink.Retrofit

import com.example.pruebacoink.Model.User

interface ResultService {
    fun onDataReturn(user: User?)
    fun onFailedResponse(message: String?)
}