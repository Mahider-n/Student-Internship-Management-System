package com.site7x24learn.internshipfrontend.domain.models.auth

data class LoginRequest(
    val email: String,
    val password: String
)