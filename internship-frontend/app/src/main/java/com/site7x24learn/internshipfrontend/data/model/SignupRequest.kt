package com.site7x24learn.internshipfrontend.data.model

import java.sql.Date

data class SignupRequest(
    val name: String,
    val gender: String,
    val birth_date: String,
    val phone: String,
    val address: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)