package com.site7x24learn.internshipfrontend.presentation.screens.student



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.site7x24learn.internshipfrontend.presentation.components.HeaderComponent
import com.site7x24learn.internshipfrontend.presentation.components.StudentInternshipCard
import com.site7x24learn.internshipfrontend.presentation.navigation.Routes
import com.site7x24learn.internshipfrontend.presentation.viewmodels.InternshipListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentInternshipListScreen(
    navController: NavHostController,
    viewModel: InternshipListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadInternships()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header Section
        HeaderComponent(
            title = "Available Internships",
            onBack = { navController.popBackStack() },
            onLogout = {
                navController.navigate(Routes.LOGIN) {
                    popUpTo(0)
                }
            },
            buttonText = "Logout"
        )

        // Main Content Section
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.errorMessage != null -> {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                state.internships.isEmpty() -> {
                    Text(
                        text = "No internships available",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(state.internships) { internship ->
                            StudentInternshipCard(
                                internship = internship,
                                onApplyClick = { internshipId ->
                                    navController.navigate(
                                        Routes.APPLY_INTERNSHIP.replace(
                                            "{internshipId}",
                                            internshipId.toString()
                                        )
                                    ) {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

//        // Bottom Navigation Buttons
//        Column(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Divider(
//                color = Color.LightGray,
//                thickness = 1.dp,
//                modifier = Modifier.padding(vertical = 32.dp)
//            )
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                // Dashboard Button
//                Button(
//                    onClick = { navController.navigate(Routes.STUDENT_DASHBOARD) },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFF1B2A80)
//                    ),
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 4.dp)
//                        .height(48.dp)
//                ) {
//                    Text("Dashboard", fontSize = 12.sp)
//                }
//
//                // Home Button
//                Button(
//                    onClick = { /* Already on this screen */ },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFF1B2A80)
//                    ),
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 4.dp)
//                        .height(48.dp)
//                ) {
//                    Text("Internships", fontSize = 12.sp)
//                }
//
//                // Profile Button
//                Button(
//                    onClick = {  },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFF1B2A80)
//                    ),
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 4.dp)
//                        .height(48.dp)
//                ) {
//                    Text("Profile", fontSize = 12.sp)
//                }
//            }
//        }
    }
}