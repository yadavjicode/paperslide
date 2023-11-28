package com.example.paper_slide.model

data class LanguageResponse (
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Language>
)

data class Language(
    val name: String,
    val code: String
)
