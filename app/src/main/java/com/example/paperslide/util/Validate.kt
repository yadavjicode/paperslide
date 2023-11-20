package com.example.paperslide.util

class Validate{
    fun validateEmail(email: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9._-]+")

        return if (email.isNotBlank()) {
            email.matches(emailPattern)
        } else {
            false
        }
    }

    fun validatePassword(password: String): Boolean {
        val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
        return if (password.isNotBlank()) {
            password.matches(passwordPattern)
        } else {
            false
        }
    }

    fun validateName(name: String): Boolean {
        return name.isNotBlank()
    }
}