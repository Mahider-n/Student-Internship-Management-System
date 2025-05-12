package com.site7x24learn.internshipfrontend.presentation.components



import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RoundedBorderButtonForApplication(
    buttonText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            disabledContainerColor = Color.White.copy(alpha = 0.5f)
        ),
        border = BorderStroke(2.dp, Color(0xFF2196F3)),
        enabled = enabled
    ) {
        Text(
            text = buttonText,
            color = if (enabled) Color(0xFF2196F3) else Color(0xFF2196F3).copy(alpha = 0.5f)
        )
    }
}