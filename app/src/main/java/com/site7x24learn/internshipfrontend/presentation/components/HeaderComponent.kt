package com.site7x24learn.internshipfrontend.presentation.components




import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderComponent(
    onLogout: () -> Unit,
    buttonText:String="Logout",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Intern",
                fontSize = 40.sp,
                color = Color(0xFF1B2A80)
            )
            Text(
                text = "Link",
                fontSize = 40.sp,
                color = Color(0xFF2196F3)
            )
        }

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            ),
            modifier = Modifier
                .width(150.dp)
                .height(50.dp)
        ) {
            Text(buttonText, fontSize = 20.sp)
        }
    }
}