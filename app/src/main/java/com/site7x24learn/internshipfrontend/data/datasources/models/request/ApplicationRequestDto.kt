package com.site7x24learn.internshipfrontend.data.datasources.models.request

import com.google.gson.annotations.SerializedName
import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationRequest

data class ApplicationRequestDto(
    @SerializedName("internship_id") val internshipId: Int,
    val university: String,
    val degree: String,
    @SerializedName("graduation_year") val graduationYear: Int,
    @SerializedName("linkdIn") val linkdIn: String
)

fun ApplicationRequest.toDto() = ApplicationRequestDto(
    internshipId = internshipId,
    university = university,
    degree = degree,
    graduationYear = graduationYear,
    linkdIn = linkdIn
)