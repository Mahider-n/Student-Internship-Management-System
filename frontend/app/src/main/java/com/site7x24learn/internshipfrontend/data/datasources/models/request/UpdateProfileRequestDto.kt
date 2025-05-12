package com.site7x24learn.internshipfrontend.data.datasources.models.request



import com.google.gson.annotations.SerializedName

data class UpdateProfileRequestDto(
    @SerializedName("name") val name: String,

    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: String,
)
