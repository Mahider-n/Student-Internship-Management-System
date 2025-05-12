package com.site7x24learn.internshipfrontend.domain.models.application

data class ApplicationRequest(
    val internshipId: Int,
    val university: String,
    val degree: String,
    val graduationYear: Int,
    val linkdIn: String
)