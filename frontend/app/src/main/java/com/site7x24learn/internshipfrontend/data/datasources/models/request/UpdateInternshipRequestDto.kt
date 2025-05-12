package com.site7x24learn.internshipfrontend.data.datasources.models.request

data class UpdateInternshipRequestDto(
    val title: String?,
    val description: String?,
    val deadline: String?,
    val company_name: String?,
    val category_id: Int?
)