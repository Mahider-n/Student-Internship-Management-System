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

@Composable
fun ApplicationCard(
    application: Application,
    onWithdraw: () -> Unit,
    onUpdate: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Internship Title
            Text(
                text = application.internshipTitle ?: "Internship",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Company Name
            Text(
                text = application.companyName ?: "Company",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Status Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                StatusIndicator(status = application.status)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = application.status.name,
                    color = when (application.status) {
                        ApplicationStatus.PENDING -> Color(0xFFFFA000) // Orange
                        ApplicationStatus.ACCEPTED -> Color(0xFF4CAF50) // Green
                        ApplicationStatus.REJECTED -> Color(0xFFF44336) // Red
                        else -> Color.Gray
                    }
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Application Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "University",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Text(
                        text = application.university,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column {
                    Text(
                        text = "Graduation Year",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Text(
                        text = application.graduationYear.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (application.status == ApplicationStatus.PENDING) {
                    OutlinedButton(
                        onClick = onWithdraw,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFF44336) // Red
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Withdraw")
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                }

                Button(
                    onClick = onUpdate,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1B2A80) // Dark blue
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Update")
                }
            }
        }
    }
}

