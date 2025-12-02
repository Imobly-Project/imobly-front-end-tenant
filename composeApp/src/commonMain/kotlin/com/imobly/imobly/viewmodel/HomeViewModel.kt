package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.TOKEN
import kotlinx.coroutines.launch

class HomeViewModel(private val navController: NavHostController): ViewModel() {
    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

    fun goToMyProfile() {
        if (isLogged())
            navController.navigate("profile")
        else
            goToLogin()
    }

    fun goToLogin() {
        TOKEN = ""
        navController.navigate("login")
    }

    fun logOut() {
        TOKEN = ""
        goToHome()
        viewModelScope.launch {
            snackMessage.value.showSnackbar("Sua conta foi deslogada sucesso!")
        }
    }

    fun isLogged() = TOKEN.isNotEmpty()

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToShowProperties() {
        navController.navigate("showproperties")
    }

    fun goToSendEmail() {
        navController.navigate("sendemail")
    }

    fun goToShowReports() {
        if (isLogged())
            navController.navigate("showreports")
        else
            goToLogin()
    }

    fun goToShowLeases() {
        if (isLogged())
            navController.navigate("showleases")
        else
            goToLogin()
    }

}