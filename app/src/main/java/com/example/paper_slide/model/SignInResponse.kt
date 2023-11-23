package com.example.paper_slide.model

data class SignInResponse(
    val message:String,
    val access_token :String,
    val refresh_token: String,
    val detail : String
)
