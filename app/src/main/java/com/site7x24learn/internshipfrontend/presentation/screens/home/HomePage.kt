package com.site7x24learn.internshipfrontend.presentation.screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.site7x24learn.internshipfrontend.R
import com.site7x24learn.internshipfrontend.presentation.components.HeaderComponent
import com.site7x24learn.internshipfrontend.presentation.viewmodels.LandingViewModel


@Composable
fun HomePage(
    navController: NavController,
    viewModel: LandingViewModel= viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { route ->
            when(route){
                "waiting"->navController.navigate(route)
                "signup","login"->navController.navigate(route){
                    popUpTo("waiting"){inclusive=true}
                }

            }
//            navController.navigate(route) {
//                popUpTo("landing") { inclusive = true }
//            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)

        ){
            HeaderComponent(
                onLogout = { viewModel.navigateWithLoading("login")},
                buttonText = "Login"

            )

        }

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            // Image
            Image(
                painter = painterResource(id = R.drawable.landing_image),
                contentDescription = "App Logo",
                modifier = Modifier.size(220.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Text
            Text(
                text = "Connecting Talent with Opportunity",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF1B2A80),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))


            // Button
            Button(
                onClick = { viewModel.navigateWithLoading("signup") },
                modifier = Modifier.width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                )
            ) {
                Text("Get Started",
                    style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

