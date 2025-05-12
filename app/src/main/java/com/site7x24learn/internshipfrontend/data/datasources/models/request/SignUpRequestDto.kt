package com.site7x24learn.internshipfrontend.data.datasources.models.request

import com.google.gson.annotations.SerializedName

data class SignUpRequestDto(
    val name: String,
    val email: String,
    val password: String,
    @SerializedName("confirmPassword")
    val confirmPassword: String,  // Add this
    val gender: String,
    @SerializedName("birth_date")
    val birthDate: String,
    val phone: String,
    val address: String
)