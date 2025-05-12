package com.site7x24learn.internshipfrontend.domain.models.application



import androidx.compose.ui.graphics.Color
import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationStatus

fun ApplicationStatus.getColor(): Color {
    return when (this) {
        ApplicationStatus.ACCEPTED -> Color(0xFF4CAF50) // Green
        ApplicationStatus.REJECTED -> Color(0xFFF44336) // Red
        ApplicationStatus.PENDING -> Color(0xFFFFC107)  // Amber/Yellow
    }
}

fun ApplicationStatus.getDisplayName(): String {
    return when (this) {
        ApplicationStatus.ACCEPTED -> "Accepted"
        ApplicationStatus.REJECTED -> "Rejected"
        ApplicationStatus.PENDING -> "Pending"
    }
}