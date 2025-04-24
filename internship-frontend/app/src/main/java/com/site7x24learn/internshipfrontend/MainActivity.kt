package com.site7x24learn.internshipfrontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.site7x24learn.internshipfrontend.ui.screens.login.LoginScreen
import com.site7x24learn.internshipfrontend.ui.screens.login.SignUpScreen
import com.site7x24learn.internshipfrontend.ui.screens.navigation.AppNavGraph
import com.site7x24learn.internshipfrontend.ui.theme.AppTheme
import com.site7x24learn.internshipfrontend.ui.theme.InternshipFrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController= rememberNavController()
            AppNavGraph(navController)

        }
    }
}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    InternshipFrontendTheme {
//        Greeting("Android")
//    }
//}
//@Preview
//@Composable
//fun PreviewLoginPage() {
//    AppTheme {
//        LoginScreen()
//    }
//}
//@Preview
//@Composable
//fun PreviewSignUpPage(){
//    AppTheme {
//        SignUpScreen()
//    }
//}
//InternshipFrontendTheme {
//    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//        Greeting(
//            name = "Android",
//            modifier = Modifier.padding(innerPadding)
//        )
//    }
//}