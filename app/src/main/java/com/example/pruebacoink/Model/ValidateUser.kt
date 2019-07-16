package com.example.pruebacoink.Model

import com.example.pruebacoink.Base.BaseModel
import com.google.gson.annotations.SerializedName

class ValidateUser : BaseModel() {

    @SerializedName("document_number")
    var document_number: String? = null
    @SerializedName("document_type")
    var document_type = "1"
    @SerializedName("birth_date")
    var birth_date: String? = null
    @SerializedName("expedition_date")
    var expedition_date: String? = null
    @SerializedName("is_Ordinary")
    var is_Ordinary = false
}