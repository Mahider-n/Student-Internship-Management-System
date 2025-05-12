package com.site7x24learn.internshipfrontend.presentation.screens.common



import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.site7x24learn.internshipfrontend.R
import com.site7x24learn.internshipfrontend.presentation.viewmodels.LandingViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WaitingPage(navController: NavHostController,viewModel: LandingViewModel= viewModel()){
    var showError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var navigated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { route ->
            if (route != "waiting" && !navigated) {
                navigated = true
                navController.navigate(route) {
                    popUpTo("waiting") { inclusive = true }
                }
            }
        }
    }

//    LaunchedEffect(Unit) {
//        coroutineScope.launch {
//            delay(500)
//            showError = true
//        }
//    }
    // Infinite animation setup
    val infiniteTransition = rememberInfiniteTransition()

    // Dot animations with staggered delays
    val dot1Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val dot2Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing, delayMillis = 200),
            repeatMode = RepeatMode.Reverse
        )
    )

    val dot3Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing, delayMillis = 400),
            repeatMode = RepeatMode.Reverse
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.landing_image),
            contentDescription = "Loading",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))
//animated dots row
        Row {
            AnimatedDot(offset = dot1Offset)
            Spacer(modifier = Modifier.width(8.dp))
            AnimatedDot(offset = dot2Offset)
            Spacer(modifier = Modifier.width(8.dp))
            AnimatedDot(offset = dot3Offset)
        }
    }

}

@Composable
private fun AnimatedDot(offset: Float) {
    Box(
        modifier = Modifier
            .offset(y = (-offset).dp)
            .size(12.dp)
            .clip(MaterialTheme.shapes.small)
            .background(Color.Blue)
    )
}
