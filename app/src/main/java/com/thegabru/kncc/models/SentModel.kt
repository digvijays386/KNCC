package com.thegabru.kncc.models

import com.google.gson.annotations.SerializedName

data class SentModel(
     val contactId: Int,
    @SerializedName("First_Name") val fname: String,
    @SerializedName("Last_Name") val lname: String,
    @SerializedName("Phone_Number") val contactNo: String,
    @SerializedName("Time") val time: String,
    @SerializedName("OTP") val otp: String
)