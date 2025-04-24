package com.site7x24learn.internshipfrontend.data.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String? // If your backend sends a JWT token
)