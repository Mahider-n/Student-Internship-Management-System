package com.site7x24learn.internshipfrontend.ui.screens.login

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
//import com.example.app.ui.theme.AppTheme
import com.site7x24learn.internshipfrontend.ui.theme.AppTheme
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
 import androidx.compose.material3.TextField



// OR
import androidx.compose.material3.TextField // For Material 3 (if you're using Compose Material 3)
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController


@Composable

fun LoginScreen(navController: NavHostController) {

    AppTheme {
        // Column to hold everything
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(36.dp)
                .paddingFromBaseline(120.dp,90.dp)
                ,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            // Blue title text
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
            // Login text
            Text(
                text = "Login",
                fontSize = 35.sp,
                color = Color(0xFF1B2A80),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Small text (Please login to continue)
            Text(
                text = "Please login to continue",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 26.dp)
            )

            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

// Email field
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 26.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

// Password field
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter password") },
                placeholder = { Text("Password") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 26.dp)
            )






            // Forgot password text
            Text(

                text = "Forgot password?",
                color = Color(0xFF2196F3),
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .align(Alignment.End)
            )

            // Login button
            Button(
                {}, Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)

            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            // Don't have an account? Sign Up
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account?",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 8.dp)

                )
                Text(
                    text = "Sign Up",
                    color = Color(0xFF2196F3),
                    fontSize = 18.sp,
                    modifier=Modifier.clickable {
                        navController.navigate("signup")
                    }
                )
            }
        }
    }

}


