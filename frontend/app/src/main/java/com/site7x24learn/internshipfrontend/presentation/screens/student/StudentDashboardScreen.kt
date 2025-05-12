package com.site7x24learn.internshipfrontend.presentation.screens.student



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.site7x24learn.internshipfrontend.domain.models.application.Application
import com.site7x24learn.internshipfrontend.presentation.components.ApplicationCard
import com.site7x24learn.internshipfrontend.presentation.components.HeaderComponent
import com.site7x24learn.internshipfrontend.presentation.navigation.Routes
import com.site7x24learn.internshipfrontend.presentation.viewmodels.StudentDashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardScreen(
    navController: NavController,
    viewModel: StudentDashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    // Auto-load on first launch and when returning from other screens
    LaunchedEffect(Unit) {
        viewModel.loadApplications()
    }

    Scaffold(
        topBar = {
            HeaderComponent(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading && !isRefreshing -> LoadingScreen()
                state.applications.isEmpty() -> EmptyScreen()
                else -> ApplicationsList(
                    applications = state.applications,
                    padding = innerPadding,
                    viewModel = viewModel,
                    navController = navController
                )
            }

            // Show error message if exists
            state.error?.let { error ->
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    action = {
                        TextButton(
                            onClick = { viewModel.loadApplications() }
                        ) {
                            Text("Retry")
                        }
                    }
                ) {
                    Text(error)
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No applications found",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Apply to internships to see them here",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}


@Composable
private fun ApplicationsList(
    applications: List<Application>,
    padding: PaddingValues,
    viewModel: StudentDashboardViewModel,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = applications,
            key = { it.id }
        ) { application ->
            ApplicationCard(
                application = application,
                onWithdraw = {
                    viewModel.deleteApplication(application.id) {
                        // Optional: Show snackbar or confirmation
                    }
                },
                onUpdate = {
                    // Prepare updates (example - modify as needed)
                    val updates = buildMap<String, Any> {
                        put("university", application.university)
                        put("degree", application.degree)
                        put("graduationYear", application.graduationYear)
                        application.linkdIn?.let { put("linkdIn", it) } // Only include if not null
                    }

                    viewModel.updateApplication(application.id, updates) {
                        navController.navigate(
                            Routes.applyInternshipRoute(application.internshipId, application.id)
                        )
                        {
                            launchSingleTop = true
                            restoreState = true
                        }


                    }
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // other UI elements...

                Button(
                    onClick = { navController.navigate(Routes.INTERNSHIP_LIST) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1B2A80)
                    ),
                    modifier = Modifier
                        .padding(top = 40.dp) // <-- top padding
                        .height(48.dp)
                ) {
                    Text("Dashboard", fontSize = 12.sp)
                }

                // other UI elements...
            }

        }
    }
}
