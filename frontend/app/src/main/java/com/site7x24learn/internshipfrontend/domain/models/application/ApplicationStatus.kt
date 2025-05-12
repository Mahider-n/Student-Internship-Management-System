package com.site7x24learn.internshipfrontend.domain.models.application

enum class ApplicationStatus {
    PENDING, ACCEPTED, REJECTED;

    companion object {
        fun fromString(status: String): ApplicationStatus {
            return try {
                valueOf(status.uppercase())
            } catch (e: IllegalArgumentException) {
                PENDING // default value if parsing fails
            }
        }
    }
}