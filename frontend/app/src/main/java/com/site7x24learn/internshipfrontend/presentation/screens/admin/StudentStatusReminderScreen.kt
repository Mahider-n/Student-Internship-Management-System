package com.site7x24learn.internshipfrontend.presentation.screens.admin


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
//import com.site7x24learn.internshipfrontend.data.repositories.PreviewStudentStatusRepository
//import com.site7x24learn.internshipfrontend.domain.models.application.StudentStatus
import com.site7x24learn.internshipfrontend.domain.repositories.StudentStatusRepository
import com.site7x24learn.internshipfrontend.presentation.components.HeaderComponent
import com.site7x24learn.internshipfrontend.presentation.components.RoundedBorderButtonForApplication

import com.site7x24learn.internshipfrontend.presentation.components.StudentStatusRow
import com.site7x24learn.internshipfrontend.presentation.navigation.Routes
//import com.site7x24learn.internshipfrontend.presentation.viewmodels.StudentStatusUiState
import com.site7x24learn.internshipfrontend.presentation.viewmodels.StudentStatusViewModel


@Composable
fun StudentStatusReminderScreen(viewModel: StudentStatusViewModel= hiltViewModel(),
                                navController: NavHostController?=null,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {


        HeaderComponent(onLogout = {
            navController?.navigate(Routes.LOGIN) {
                popUpTo(Routes.ADD_INTERNSHIP) { inclusive = true }
            }
        })
        Spacer(modifier = Modifier.height(60.dp)) // Space for header

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Name", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    Text("Company", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    Text("Resume", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    Text("Status", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                uiState.students.forEach { student ->
                    StudentStatusRow(
                        student = student,
                        onStatusChange = { newStatus ->
                            viewModel.updateStatus(student.id, newStatus)
                        }
                    )
                }
            }

        }
        RoundedBorderButtonForApplication (
            onClick = {
                navController?.navigate(Routes.ADMIN_DASHBOARD) {
                    popUpTo(Routes.STUDENT_STATUS) { inclusive = true }
                }
            },
            buttonText = "Back to Dashboard",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(20.dp)
        )
    }
}