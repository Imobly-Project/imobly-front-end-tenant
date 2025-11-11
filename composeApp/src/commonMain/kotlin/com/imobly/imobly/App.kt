package com.imobly.imobly

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.screens.home.HomeScreen
import com.imobly.imobly.ui.screens.login.LoginScreen
import com.imobly.imobly.ui.screens.signup.SignUpScreen
import com.imobly.imobly.viewmodel.LoginViewModel
import com.imobly.imobly.viewmodel.TenantViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val loginViewModel = viewModel { LoginViewModel(navController) }
    val tenantViewModel = viewModel { TenantViewModel(navController) }



    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = "login"
        ){
            composable(route = "login"){
                LoginScreen(loginViewModel)
            }
            composable(route = "signup") {
                SignUpScreen(tenantViewModel)
            }
            composable(route = "home") {
                HomeScreen(navController)
            }
        }
    }
}
