package com.imobly.imobly

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.screens.appointments.CreateAppointmentScreen
import com.imobly.imobly.ui.screens.createreport.CreateReportScreen
import com.imobly.imobly.ui.screens.profile.ProfileScreen
import com.imobly.imobly.ui.screens.home.HomeScreen
import com.imobly.imobly.ui.screens.login.LoginScreen
import com.imobly.imobly.ui.screens.showlease.ShowLeasesScreen
import com.imobly.imobly.ui.screens.showreports.ShowReportsScreen
import com.imobly.imobly.ui.screens.showproperties.ShowPropertiesScreen
import com.imobly.imobly.ui.screens.signup.SignUpScreen
import com.imobly.imobly.viewmodel.AppointmentViewModel
import com.imobly.imobly.viewmodel.HomeViewModel
import com.imobly.imobly.viewmodel.LeaseViewModel
import com.imobly.imobly.viewmodel.LoginViewModel
import com.imobly.imobly.viewmodel.PropertyViewModel
import com.imobly.imobly.viewmodel.ReportViewModel
import com.imobly.imobly.viewmodel.TenantViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val loginViewModel = viewModel { LoginViewModel(navController) }
    val tenantViewModel = viewModel { TenantViewModel(navController) }
    val propertyViewModel = viewModel { PropertyViewModel(navController) }
    val reportViewModel = viewModel { ReportViewModel(navController) }
    val leaseViewModel = viewModel { LeaseViewModel(navController) }
    val homeViewModel = viewModel { HomeViewModel(navController) }
    val appointmentViewModel = viewModel { AppointmentViewModel(navController) }

    MaterialTheme {
        NavHost(navController = navController, startDestination = "home") {

            composable(route = "login") {
                LoginScreen(loginViewModel)
            }

            composable(route = "signup") {
                SignUpScreen(tenantViewModel)
            }

            composable(route = "home") {
                HomeScreen(homeViewModel)
            }

            composable(route = "profile") {
                ProfileScreen(tenantViewModel)
            }

            composable(route = "createreport") {
                CreateReportScreen(reportViewModel)
            }

            composable(route = "showreports") {
                ShowReportsScreen(reportViewModel)
            }

            composable(route = "showproperties") {
                ShowPropertiesScreen(propertyViewModel)
            }

            composable(route = "showleases") {
                ShowLeasesScreen(leaseViewModel)
            }

            composable(route = "createappointment") {
                CreateAppointmentScreen(appointmentViewModel)
            }
        }
    }
}
