package com.site7x24learn.internshipfrontend.domain.models.application

data class Application(
    val id: Int,  // Must be 'id' (not applicationId or something else)
    val userId: Int,
    val internshipId: Int,
    val university: String,
    val degree: String,
    val graduationYear: Int,
    val linkdIn: String?,
    val status: ApplicationStatus,
    val appliedAt: String,
    val resumeId: Int,
    val attachmentPath: String?,
    val internshipTitle: String?,
    val companyName: String?
)
