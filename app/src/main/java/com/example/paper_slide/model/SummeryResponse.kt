package com.example.paper_slide.model

data class SummeryResponse (
    val id : Int,
    val original_text : String,
    val summarized_text: String,
    val summary_length : Int,
    val date : String,
    val last_update : String,
    val user : Int,
    val Status : Int
)