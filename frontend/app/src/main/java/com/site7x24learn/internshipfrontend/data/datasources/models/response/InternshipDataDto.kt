package com.site7x24learn.internshipfrontend.data.datasources.models.response

data class InternshipDataDto(
    val internship_id: Int,  // Note the underscore
    val title: String,
    val description: String?,
    val deadline: String,
    val is_active: Int,  // Underscore
    val status: String,
    val company_name: String,  // Underscore
    val category_name: String,  // Underscore
    val created_by_name: String,  // Underscore
    val created_at: String,  // Underscore
    val updated_at: String  // Underscore
)