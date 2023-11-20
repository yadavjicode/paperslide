package com.example.paperslide.model

data class SignInResponse(
    val message:String,
    val access_token :String,
    val refresh_token: String
)
