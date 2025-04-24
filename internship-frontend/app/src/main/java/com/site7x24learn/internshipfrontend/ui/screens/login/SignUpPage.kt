package com.site7x24learn.internshipfrontend.ui.screens.login

import android.util.Log
import android.view.WindowInsetsAnimation
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.site7x24learn.internshipfrontend.data.model.SignupRequest
import com.site7x24learn.internshipfrontend.data.model.SignupResponse
import com.site7x24learn.internshipfrontend.data.network.RetrofitClient
import org.json.JSONObject
import java.sql.Date
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun SignUpScreen(navController:NavHostController) {
    var signupErrorMessage by remember { mutableStateOf<String?>(null) }

    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .padding(6.dp)
            .paddingFromBaseline(90.dp,60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
//                title text Link
            Text(
                text = "Intern",
                fontSize = 40.sp,
                color = Color(0xFF1B2A80),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Title text (Intern)
            Text(
                text = "Link",
                fontSize = 40.sp,
                color = Color(0xFF2196F3),
                modifier = Modifier.padding(bottom = 16.dp)

            )

        }
        Text("Let's get started!", fontSize = 34.sp, color = Color(0xFF1B2A80))
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an account?", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "Sign in",
                fontSize = 16.sp,
                color = Color(0xFF2196F3),
                modifier = Modifier.clickable {
                    navController.navigate("login")

                }
            )
        }
        signupErrorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                CustomTextField("Name", name,placeholder =  "Enter your Full name") { name = it }
                CustomTextField("Gender", gender, placeholder = "Male or Female") { gender = it }
                CustomTextField("Date of Birth", dob, placeholder = "YYYY-MM-DD") { dob = it }
                CustomTextField("Contact number", phone, KeyboardType.Phone, placeholder = "e.g., +251912341211") { phone = it }
                CustomTextField("Address", address, placeholder = "Enter your current address") { address = it }
                CustomTextField("Email", email, KeyboardType.Email, placeholder = "example@email.com") { email = it }
                CustomTextField("Enter Password", password, KeyboardType.Password, true, placeholder = "Create a strong password") { password = it }
                CustomTextField("Confirm Password", confirmPassword, KeyboardType.Password, true, placeholder = "Re-enter your password") { confirmPassword = it }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        dateFormat.isLenient = false

                        try {
                            dateFormat.parse(dob) // Validate date format

                            val signupRequest = SignupRequest(
                                name, gender, dob, phone, address, email, password, confirmPassword
                            )

                            RetrofitClient.apiService.signup(signupRequest).enqueue(object : Callback<SignupResponse> {
                                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                                    val responseBody = response.body()
                                    val errorBody = response.errorBody()?.string()

                                    Log.d("Signup", "Raw response: ${response.raw()}")
                                    Log.d("Signup", "Response body: $responseBody")
                                    Log.d("Signup", "Error body: $errorBody")

                                    if (response.isSuccessful && responseBody != null) {
                                        signupErrorMessage = null
                                        // Navigate to login or show success
                                        navController.navigate("login") {
                                            popUpTo("signup") { inclusive = true }
                                        }
                                    } else {
                                        try {
                                            val json = JSONObject(errorBody)
                                            signupErrorMessage = when {
                                                json.has("errors") -> {
                                                    val errors = json.getJSONArray("errors")
                                                    val messages = mutableListOf<String>()
                                                    for (i in 0 until errors.length()) {
                                                        messages.add(errors.getJSONObject(i).getString("msg"))
                                                    }
                                                    messages.joinToString("\n")
                                                }
                                                json.has("message") -> json.getString("message") // e.g., "User already exists"
                                                else -> "Something went wrong."
                                            }
                                        } catch (e: Exception) {
                                            signupErrorMessage = "Unexpected error occurred."
                                            Log.e("Signup", "JSON parse error: ${e.message}")
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                                    Log.e("Signup", "Network failure: ${t.message}")
                                    signupErrorMessage = "Network error: ${t.message}"
                                }
                            })

                        } catch (e: ParseException) {
                            signupErrorMessage = "Invalid date format! Use YYYY-MM-DD"
                        }
                    }
                    ,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3),
                         )
                ) {
                    Text("Sign up", fontSize = 20.sp, color = Color.White)
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
    placeholder: String = label,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(text = label, fontSize = 20.sp, modifier = Modifier.padding(bottom = 4.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
