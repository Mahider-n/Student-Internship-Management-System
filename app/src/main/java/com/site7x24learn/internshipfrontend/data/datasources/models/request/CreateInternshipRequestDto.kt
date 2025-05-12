package com.site7x24learn.internshipfrontend.data.datasources.models.request

data class CreateInternshipRequestDto(
    val title: String,
    val description: String? = null,
    val deadline: String,
    val company_name: String,
    val category_id: Int
)