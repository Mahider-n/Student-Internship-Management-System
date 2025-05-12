package com.site7x24learn.internshipfrontend.domain.models.internships

data class Internship(
    val id: Int,
    val title: String,
    val description: String?,
    val deadline: String,
    val companyName: String,
    val categoryName: String,
    val createdByName: String,
    val createdAt: String,
    val status: String,
    val isActive: Int
)