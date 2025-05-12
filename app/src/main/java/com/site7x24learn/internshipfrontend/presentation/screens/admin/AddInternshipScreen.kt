package com.site7x24learn.internshipfrontend.presentation.screens.admin



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.site7x24learn.internshipfrontend.presentation.components.*
import com.site7x24learn.internshipfrontend.presentation.navigation.Routes
import com.site7x24learn.internshipfrontend.presentation.viewmodels.InternshipEvent
import com.site7x24learn.internshipfrontend.presentation.viewmodels.InternshipViewModel
import kotlinx.coroutines.delay

@Composable
fun AddInternships(
    internshipId: Int? = null,
    navController: NavHostController,
    viewModel: InternshipViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    LaunchedEffect(Unit) {
        if (internshipId != null) {
            viewModel.loadInternship(internshipId)
        }
    }
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            delay(1500) // Show success message for 1.5 seconds
            navController.navigate(Routes.ADMIN_DASHBOARD) {
                popUpTo(Routes.ADD_INTERNSHIP) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderComponent(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            onLogout = {
                navController.navigate(Routes.LOGIN) {
                    popUpTo(0)
                }
            }
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (state.isSuccess) {
                    Text(
                        text = "Internship created successfully!",
                        color = Color.Green,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                FormSection(title = "Job Title") {
                    OutlinedTextField(
                        value = state.title,
                        onValueChange = { viewModel.onEvent(InternshipEvent.TitleChanged(it)) },
                        label = { Text("Enter Job Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                FormSection(title = "Company Name") {
                    OutlinedTextField(
                        value = state.company,
                        onValueChange = { viewModel.onEvent(InternshipEvent.CompanyChanged(it)) },
                        label = { Text("Enter company name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                FormSection(title = "Requirements / Description") {
                    OutlinedTextField(
                        value = state.description,
                        onValueChange = { viewModel.onEvent(InternshipEvent.DescriptionChanged(it)) },
                        label = { Text("Enter Requirements or Description") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5
                    )
                }

                FormSection(title = "Deadline") {
                    OutlinedTextField(
                        value = state.deadline,
                        onValueChange = { viewModel.onEvent(InternshipEvent.DeadlineChanged(it)) },
                        label = { Text("Enter deadline (YYYY-MM-DD)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                FormSection(title = "Category") {
                    CategoryDropdown(
                        categories = state.categories,
                        selectedCategoryId = state.selectedCategoryId,
                        onCategorySelected = { id ->
                            viewModel.onEvent(InternshipEvent.CategoryChanged(id))
                        }
                    )
                }

                if (state.error != null) {
                    Text(
                        text = state.error ?: "An error occurred",
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }


                FormButtons(
                    onSave = {
                        if (internshipId != null) {
                            viewModel.onEvent(InternshipEvent.Update(internshipId))
                        } else {
                            viewModel.onEvent(InternshipEvent.Submit)
                        }
                    },
//                    onSave = { viewModel.onEvent(InternshipEvent.Submit) },
                    onCancel = {
                        viewModel.onEvent(InternshipEvent.Reset)
                        navController.navigate(Routes.ADMIN_DASHBOARD) {
                            popUpTo(Routes.ADMIN_DASHBOARD) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
