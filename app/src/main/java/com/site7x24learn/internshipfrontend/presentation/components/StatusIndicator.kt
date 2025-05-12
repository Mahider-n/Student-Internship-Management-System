package com.site7x24learn.internshipfrontend.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationStatus

@Composable
fun StatusIndicator(status: ApplicationStatus) {
    val color = when (status) {
        ApplicationStatus.PENDING -> Color(0xFFFFA000)
        ApplicationStatus.ACCEPTED -> Color(0xFF4CAF50)
        ApplicationStatus.REJECTED -> Color(0xFFF44336)
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .size(12.dp)
            .background(color, shape = RoundedCornerShape(4.dp))
    )
}
