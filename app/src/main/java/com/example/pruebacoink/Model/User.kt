package com.example.pruebacoink.Model

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("first_name")
    var first_name: String? = null
    @SerializedName("second_name")
    var second_name: String? = null
    @SerializedName("first_last_name")
    var first_last_name: String? = null
    @SerializedName("second_last_name")
    var second_last_name: String? = null
    @SerializedName("gender")
    var gender = Gender()

    class Gender {
        @SerializedName("Ordinal")
        var Ordinal: Int? = null
        @SerializedName("Name")
        var Name: String? = null
        @SerializedName("Description")
        var Description: String? = null
    }
}