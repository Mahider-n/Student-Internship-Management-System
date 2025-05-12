package com.site7x24learn.internshipfrontend.domain.models.auth

data class AuthUser(
    val id: Int = -1,
    val name: String = "",
    val email: String = "",
    val gender: String = "",
    val birthDate: String = "",
    val phone: String = "",
    val address: String = "",
    val role: String = "student",
    val token: String = ""
)