package com.site7x24learn.internshipfrontend.presentation.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.site7x24learn.internshipfrontend.domain.models.application.Application
import com.site7x24learn.internshipfrontend.domain.models.application.ApplicationStatus
import com.site7x24learn.internshipfrontend.domain.models.application.getColor
import com.site7x24learn.internshipfrontend.domain.models.application.getDisplayName

@Composable
fun StudentApplicationCard(
    application: Application,
    onUpdateClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    canUpdate: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD6EAF8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Status Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = application.status.getColor(),
                            shape = RoundedCornerShape(50)
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Status: ${application.status.getDisplayName()}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            // Internship Title
            Text(
                text = application.internshipTitle ?: "No Title",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Company Name
            Text(
                text = "Company: ${application.companyName ?: "Unknown"}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // University
            Text(
                text = "University: ${application.university}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Applied Date
            Text(
                text = "Applied: ${application.appliedAt ?: "Unknown date"}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(4.dp))
//
//            // Deadline
//            Text(
//                text = "Deadline: ${application.internshipDeadline ?: "No deadline"}",
//                style = MaterialTheme.typography.bodySmall
//            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onWithdrawClick,
                    modifier = Modifier.weight(1f).padding(end = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5252)
                    )
                ) {
                    Text("Withdraw")
                }

                Button(
                    onClick = onUpdateClick,
                    modifier = Modifier.weight(1f).padding(start = 4.dp),
                    enabled = canUpdate,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (canUpdate) Color(0xFF2196F3) else Color.Gray
                    )
                ) {
                    Text("Update")
                }
            }
        }
    }
}