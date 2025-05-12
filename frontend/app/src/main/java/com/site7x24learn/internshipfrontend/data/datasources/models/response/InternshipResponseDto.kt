package com.site7x24learn.internshipfrontend.data.datasources.models.response


data class InternshipResponseDto(
    val success: Boolean,
    val data: InternshipDataDto,
    val message: String
)