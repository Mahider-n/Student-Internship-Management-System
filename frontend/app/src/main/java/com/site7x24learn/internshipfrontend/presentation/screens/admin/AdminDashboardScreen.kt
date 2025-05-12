package com.site7x24learn.internshipfrontend.presentation.screens.admin







import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.site7x24learn.internshipfrontend.domain.models.internships.Internship
import com.site7x24learn.internshipfrontend.presentation.components.HeaderComponent
import com.site7x24learn.internshipfrontend.presentation.components.InternshipCard
//import com.site7x24learn.internshipfrontend.presentation.components.RoundedBorderButton
import com.site7x24learn.internshipfrontend.presentation.components.RoundedBorderButtonForApplication
import com.site7x24learn.internshipfrontend.presentation.navigation.Routes
import com.site7x24learn.internshipfrontend.presentation.viewmodels.InternshipListState
import com.site7x24learn.internshipfrontend.presentation.viewmodels.InternshipListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(
    navController: NavController,
    viewModel: InternshipListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    // This will refresh internships every time the screen is recomposed
    LaunchedEffect(Unit) {
        viewModel.loadInternships()
    }
    // Handle side effects like showing snackbars for delete operations
    LaunchedEffect(key1 = state.errorMessage) {
        state.errorMessage?.let { message ->
            // You'll need a scaffoldState here to show snackbar
            // scaffoldState.snackbarHostState.showSnackbar(message)
            viewModel.onErrorMessageShown()
        }
    }
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(6.dp)
                    .paddingFromBaseline(50.dp, 60.dp),
            ) {
                HeaderComponent(onLogout = {
                    navController.navigate("login"){
                        popUpTo("add_internship"){inclusive=true}
                    }
                })
                Row(
                    modifier = Modifier
                        .padding(top = 30.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dashboard",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(12.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                    Box(Modifier.height(36.dp)){
                        RoundedBorderButtonForApplication ("Review Application"
                        ) {navController.navigate(Routes.STUDENT_STATUS)}
                    }
                }
                HorizontalDivider()

            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Routes.ADD_INTERNSHIP) },
                icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                text = { Text("Add Internship") }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.internships.isEmpty() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No internships available")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.navigate(Routes.ADD_INTERNSHIP) }
                        ) {
                            Text("Create First Internship")
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.internships) { internship ->
                            InternshipCard(
                                internship = internship,
                                onClick = {
                                    navController.navigate("internship_detail/${internship.id}")
                                },
                                onEdit = {
                                    navController.navigate(Routes.editInternshipRoute(internship.id))
                                },
                                onDelete = {
                                    viewModel.deleteInternship(internship.id)
                                },
                                isAdmin = true
                            )
                        }
                    }
                }
            }
        }
    }
}