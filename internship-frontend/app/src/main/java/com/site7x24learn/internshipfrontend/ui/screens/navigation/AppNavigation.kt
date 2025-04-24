package com.site7x24learn.internshipfrontend.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.site7x24learn.internshipfrontend.ui.screens.login.LoginScreen
import com.site7x24learn.internshipfrontend.ui.screens.login.SignUpScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "signup") {
        composable("signup") {
            SignUpScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
    }
}
