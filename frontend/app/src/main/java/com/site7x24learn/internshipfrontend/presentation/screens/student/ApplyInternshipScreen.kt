

package com.site7x24learn.internshipfrontend.presentation.screens.student


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.site7x24learn.internshipfrontend.presentation.components.CustomTextField
import com.site7x24learn.internshipfrontend.presentation.components.HeaderComponent
import com.site7x24learn.internshipfrontend.presentation.components.RoundedBorderButtonForApplication
import com.site7x24learn.internshipfrontend.presentation.navigation.Routes
//import com.site7x24learn.internshipfrontend.presentation.viewmodels.ApplyInternshipEvent
import com.site7x24learn.internshipfrontend.presentation.viewmodels.ApplyInternshipViewModel
import com.site7x24learn.internshipfrontend.presentation.viewmodels.StudentDashboardViewModel

@Composable
fun ApplyInternshipScreen(
    navController: NavController,
//    navController:navCon
    applicationId: Int = -1,
    internshipId: Int?,
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: ApplyInternshipViewModel = hiltViewModel(),
    dashboardViewModel: StudentDashboardViewModel = hiltViewModel()
) {
    LaunchedEffect(applicationId) {
        if (applicationId > 0) {
            viewModel.isEditMode = true
            viewModel.loadExistingApplication(applicationId)
        } else {
            viewModel.resetStateForEdit()
        }
    }

    val state by viewModel.state.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val fileName = context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val displayNameIndex = cursor.getColumnIndex("_display_name")
                    cursor.getString(displayNameIndex)
                } else null
            }
            fileName?.let { name ->
                viewModel.onEvent(ApplyInternshipViewModel.ApplyInternshipEvent.OnResumeSelected(uri, name))
            }
        }
    }

    LaunchedEffect(uiState.isApplicationSubmitted) {
        if (uiState.isApplicationSubmitted) {
            dashboardViewModel.refreshApplications()
            onSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderComponent(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            onLogout = {
                navController.navigate(Routes.LOGIN) {
                    popUpTo(0)
                }
            }
        )


        Spacer(modifier = Modifier.height(24.dp))

        uiState.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }


        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFF8E98A8)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                CustomTextField(
                    label = "University",
                    value = state.university,
                    onValueChange = {
                        viewModel.onEvent(ApplyInternshipViewModel.ApplyInternshipEvent.OnUniversityChange(it))
                    },
//                    error = uiState.fieldErrors["university"]
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextField(
                    label = "Degree",
                    value = state.degree,
                    onValueChange = {
                        viewModel.onEvent(ApplyInternshipViewModel.ApplyInternshipEvent.OnDegreeChange(it))
                    },
//                    error = uiState.fieldErrors["degree"]
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextField(
                    label = "Graduation Year",
                    value = state.graduationYear,
                    onValueChange = {
                        viewModel.onEvent(ApplyInternshipViewModel.ApplyInternshipEvent.OnGraduationYearChange(it))
                    },
                    keyboardType = KeyboardType.Number,
//                    error = uiState.fieldErrors["graduationYear"]
                )
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextField(
                    label = "LinkedIn",
                    value = state.linkedIn,
                    onValueChange = {
                        viewModel.onEvent(ApplyInternshipViewModel.ApplyInternshipEvent.OnLinkedInChange(it))
                    },
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { filePickerLauncher.launch("application/pdf") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = state.resumeFileName ?: "Upload Resume")
                }
                uiState.fieldErrors["resume"]?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }

        }

        Spacer(modifier = Modifier.height(24.dp))

        RoundedBorderButtonForApplication(
            buttonText = if (viewModel.isEditMode) "Update Application" else "Submit Application",
            onClick = {
                if (viewModel.isEditMode && applicationId > 0) {
                    viewModel.onEvent(ApplyInternshipViewModel.ApplyInternshipEvent.UpdateApplication(applicationId, context))
                } else {
                    internshipId?.let {
                        viewModel.onEvent(ApplyInternshipViewModel.ApplyInternshipEvent.SubmitApplication(it, context))
                    }
                }
            },
        )
    }
}
























