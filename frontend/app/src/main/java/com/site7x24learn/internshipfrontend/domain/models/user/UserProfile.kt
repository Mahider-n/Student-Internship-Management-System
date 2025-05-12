package com.site7x24learn.internshipfrontend.domain.models.user

data class UserProfile(
    val id: Int,
    val name: String,
    val email: String?,
    val birthDate: String?,
    val phone: String?,
    val address: String?,
    val role: String?
)
