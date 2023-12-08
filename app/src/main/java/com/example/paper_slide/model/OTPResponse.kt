package com.example.paper_slide.model

data class OTPResponse(
    val type : String,
    val status : String,
    val message :String,
    val access_token :String

)
