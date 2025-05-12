package com.site7x24learn.internshipfrontend.presentation.screens.auth


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.site7x24learn.internshipfrontend.presentation.navigation.Routes
import com.site7x24learn.internshipfrontend.presentation.theme.InternshipFrontendTheme
import com.site7x24learn.internshipfrontend.presentation.viewmodels.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Debugging
    LaunchedEffect(Unit) {
        println("DEBUG: LoginScreen composable initialized")
    }

    LaunchedEffect(authState) {
        println("DEBUG: AuthState changed: $authState")

        when (authState) {
            is AuthViewModel.AuthState.Success -> {
                val user = (authState as AuthViewModel.AuthState.Success).user
                println("DEBUG: Login successful - User role: ${user.role}")

                withContext(Dispatchers.Main) {
                    when (user.role) {
                        "admin" -> {
                            println("DEBUG: Navigating to admin dashboard")
                            Toast.makeText(context, "Welcome Admin!", Toast.LENGTH_SHORT).show()
                            navController.navigate(Routes.ADMIN_DASHBOARD) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        }
                        "student" -> {
                            println("DEBUG: Navigating to student dashboard")
                            Toast.makeText(context, "Welcome Student!", Toast.LENGTH_SHORT).show()
                            navController.navigate(Routes.INTERNSHIP_LIST) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        }
                        else -> {
                            println("DEBUG: Unknown role: ${user.role}")
                            Toast.makeText(
                                context,
                                "Unknown role: ${user.role}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
            is AuthViewModel.AuthState.Error -> {
                val errorMessage = (authState as AuthViewModel.AuthState.Error).message
                println("DEBUG: Login error: $errorMessage")
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
            is AuthViewModel.AuthState.Loading -> {
                println("DEBUG: Login in progress...")
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
                    .padding(36.dp)
                    .paddingFromBaseline(top = 120.dp, bottom = 90.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo and title
                Row {
                    Text("Intern", fontSize = 40.sp, color = Color(0xFF1B2A80))
                    Text("Link", fontSize = 40.sp, color = Color(0xFF2196F3))
                }

                Text("Login", fontSize = 35.sp, color = MaterialTheme.colorScheme.primary)
                Text(
                    "Please login to continue",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 26.dp)
                )

                // Error message display
                if (authState is AuthViewModel.AuthState.Error) {
                    Text(
                        text = (authState as AuthViewModel.AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Email field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Enter email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                // Password field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Enter password") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

//                // Forgot password
//                Text(
//                    text = "Forgot password?",
//                    color = MaterialTheme.colorScheme.primary,
//                    fontSize = 18.sp,
//                    modifier = Modifier
//                        .align(Alignment.End)
//                        .padding(bottom = 16.dp)
//                        .clickable { /* TODO: Handle forgot password */ }
//                )

                // Login button
                Button(
                    onClick = {
                        println("DEBUG: Login button clicked")
                        viewModel.login(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(bottom = 16.dp),
                    enabled = authState !is AuthViewModel.AuthState.Loading
                ) {
                    if (authState is AuthViewModel.AuthState.Loading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text("Login", color = Color.White, fontSize = 20.sp)
                    }
                }

                // Sign up prompt
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Don't have an account?", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        modifier = Modifier.clickable {
                            navController.navigate(Routes.SIGN_UP)
                        }
                    )
                }
            }
        }
    }
}