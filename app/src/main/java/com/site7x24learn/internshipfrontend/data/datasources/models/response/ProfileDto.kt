package com.site7x24learn.internshipfrontend.data.datasources.models.response

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("birth_date") val birthDate: String?,
    @SerializedName("role") val role: String?,
    // Make these optional since they're not in the backend response
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("address") val address: String? = null
)