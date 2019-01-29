package com.thegabru.kncc.models

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("id") val contactId: Int,
    @SerializedName("First_Name") val fname: String,
    @SerializedName("Last_Name") val lname: String,
    @SerializedName("Phone_Number") val contactNo: String
)
