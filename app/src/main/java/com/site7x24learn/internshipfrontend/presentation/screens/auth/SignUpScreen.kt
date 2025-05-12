package com.site7x24learn.internshipfrontend.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.site7x24learn.internshipfrontend.presentation.navigation.Routes
import com.site7x24learn.internshipfrontend.presentation.theme.InternshipFrontendTheme
import com.site7x24learn.internshipfrontend.presentation.viewmodels.AuthViewModel

@Composable
fun SignUpScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthViewModel.AuthState.Success -> {
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.LOGIN) {
                    popUpTo(Routes.SIGN_UP) { inclusive = true }
                }
            }
            is AuthViewModel.AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState as AuthViewModel.AuthState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }

    InternshipFrontendTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Row {
                    Text("Intern", fontSize = 40.sp, color = Color(0xFF1B2A80))
                    Text("Link", fontSize = 40.sp, color = Color(0xFF2196F3))
                }

                Text(
                    "Let's get started!",
                    fontSize = 34.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Already have an account?", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Sign in",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            navController.navigate(Routes.LOGIN)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        CustomTextField(
                            label = "Name",
                            value = name,
                            placeholder = "Enter your Full name"
                        ) { name = it }

                        CustomTextField(
                            label = "Gender",
                            value = gender,
                            placeholder = "Male or Female"
                        ) { gender = it }

                        CustomTextField(
                            label = "Date of Birth",
                            value = dob,
                            placeholder = "YYYY-MM-DD"
                        ) { dob = it }

                        CustomTextField(
                            label = "Contact number",
                            value = phone,
                            keyboardType = KeyboardType.Phone,
                            placeholder = "e.g., +251912341211"
                        ) { phone = it }

                        CustomTextField(
                            label = "Address",
                            value = address,
                            placeholder = "Enter your current address"
                        ) { address = it }

                        CustomTextField(
                            label = "Email",
                            value = email,
                            keyboardType = KeyboardType.Email,
                            placeholder = "example@email.com"
                        ) { email = it }

                        CustomTextField(
                            label = "Enter Password",
                            value = password,
                            keyboardType = KeyboardType.Password,
                            isPassword = true,
                            placeholder = "Create a strong password"
                        ) { password = it }

                        CustomTextField(
                            label = "Confirm Password",
                            value = confirmPassword,
                            keyboardType = KeyboardType.Password,
                            isPassword = true,
                            placeholder = "Re-enter your password"
                        ) { confirmPassword = it }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.validateAndSignUp(
                                    name, email, password, confirmPassword,
                                    gender, dob, phone, address
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = authState !is AuthViewModel.AuthState.Loading
                        ) {
                            if (authState is AuthViewModel.AuthState.Loading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                Text("Sign up", fontSize = 20.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    placeholder: String = "",
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation()
        else VisualTransformation.None
    )
}