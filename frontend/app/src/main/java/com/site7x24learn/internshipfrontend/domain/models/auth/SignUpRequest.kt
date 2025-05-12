package com.site7x24learn.internshipfrontend.domain.models.auth

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val gender: String,
    val dob: String,
    val phone: String,
    val address: String
)