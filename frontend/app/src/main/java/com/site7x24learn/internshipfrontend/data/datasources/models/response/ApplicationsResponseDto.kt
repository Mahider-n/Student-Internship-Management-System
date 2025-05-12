package com.site7x24learn.internshipfrontend.data.datasources.models.response

import com.google.gson.annotations.SerializedName

data class ApplicationsResponseDto(
    val success: Boolean,
    val data: List<ApplicationDto>
)
// data/datasources/models/response/ApplicationDto.kt
// In ApplicationsResponseDto.kt
data class ApplicationDto(
    @SerializedName("application_id") val id: Int,
    @SerializedName("student_name") val studentName: String,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("user_id") val userId: Int,
    val status: String,
    @SerializedName("attachment_path") val resumePath: String?,
    @SerializedName("resume_id") val resumeId: Int
)
//