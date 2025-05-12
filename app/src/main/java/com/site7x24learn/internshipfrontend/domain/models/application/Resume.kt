package com.site7x24learn.internshipfrontend.domain.models.application

data class Resume(
    val resumeId: Int,
    val userId: Int,
    val originalFilename: String,
    val fileExtension: String,
    val attachmentPath: String,
    val uploadedAt: String
)