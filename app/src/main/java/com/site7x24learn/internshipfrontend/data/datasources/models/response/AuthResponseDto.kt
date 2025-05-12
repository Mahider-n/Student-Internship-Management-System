package com.site7x24learn.internshipfrontend.data.datasources.models.response

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    val success: Boolean,
    val message: String?,
    val user: UserDto?,
    val token: String?
)