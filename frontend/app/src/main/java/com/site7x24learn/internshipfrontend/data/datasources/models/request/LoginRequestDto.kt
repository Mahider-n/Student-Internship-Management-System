package com.site7x24learn.internshipfrontend.data.datasources.models.request

import com.google.gson.annotations.SerializedName


data class LoginRequestDto(
    val email: String,
    val password: String
)