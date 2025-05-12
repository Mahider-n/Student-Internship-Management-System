package com.site7x24learn.internshipfrontend.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.site7x24learn.internshipfrontend.domain.models.internships.Internship

// InternshipCard.kt
@Composable
fun StudentInternshipCard(
    internship: Internship,
    onApplyClick: (Int) -> Unit = {} // Default empty lambda instead of nullable
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD6EAF8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title
            Text(
                text = internship.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier.padding(vertical = 5.dp)
            )

            // Category
            Box(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .border(1.dp, Color(0xFF2196F3), RoundedCornerShape(8.dp))
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = internship.categoryName,
                    color = Color(0xFF2196F3),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            // Company
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                        append("Company: ")
                    }
                    withStyle(SpanStyle(color = Color(0xFF2196F3), fontWeight = FontWeight.Bold)) {
                        append(internship.companyName)
                    }
                },
                modifier = Modifier.padding(vertical = 5.dp)
            )

            // Requirements
            Text(
                text = "Requirements: \n${internship.description}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 5.dp)
            )

            // Deadline
            Text(
                text = "Deadline: ${internship.deadline}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 5.dp)
            )

            // Apply Button
            Button(
                onClick = {onApplyClick(internship.id)},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = BorderStroke(2.dp, Color(0xFF2196F3))
            ) {
                Text("Apply", color = Color(0xFF2196F3))
            }
        }
    }
}
