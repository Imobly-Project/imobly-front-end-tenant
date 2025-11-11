package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.httpclient.AuthenticationHttpClient
import com.imobly.imobly.domain.Auth
import kotlinx.coroutines.launch


class LoginViewModel(private val navController: NavHostController): ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val passwordVisibilityState = mutableStateOf(false)
    val onLoadingState = mutableStateOf(false)

    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

    val messageError = mutableStateOf("")

    fun goToSignUp() {
        navController.navigate("signup")
    }

    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun changePasswordVisibility() {
        passwordVisibilityState.value = !passwordVisibilityState.value
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun signInAction() {
        val auth = Auth(email.value, password.value)
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = AuthenticationHttpClient(createHttpClient())
            val response = httpClient.signIn(auth)
            onLoadingState.value = false
            when (response) {
                is Ok -> {
                    email.value = ""
                    password.value = ""
                    inputErrors.value = emptyMap()
                    messageError.value = ""
                    goToHome()
                }
                is ErrorDTO -> {
                    val errors = mutableMapOf<String, String>()
                    response.errorFields?.forEach { errors[it.name] = it.description }
                    inputErrors.value = errors
                    messageError.value = response.message
                }
            }
        }
    }

    fun changeEmail(it: String) {
        email.value = it
    }

    fun changePassword(it: String) {
        password.value = it
    }
}