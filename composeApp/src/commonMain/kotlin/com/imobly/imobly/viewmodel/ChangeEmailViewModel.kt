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
import com.imobly.imobly.api.dto.EmailDTO
import com.imobly.imobly.api.httpclient.TenantHttpClient
import kotlinx.coroutines.launch

class ChangeEmailViewModel(private val navController: NavHostController) : ViewModel() {

    val email = mutableStateOf("")
    val code = mutableStateOf("")

    val onLoadingState = mutableStateOf(false)

    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val snackMessage: MutableState<SnackbarHostState> = mutableStateOf(SnackbarHostState())

    val messageError = mutableStateOf("")


    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun goToLogin() {
        navController.navigate("login")
    }

    fun goToSendCode() {
        navController.navigate("sendcode")
    }

    fun goToSendEmail() {
        navController.navigate("sendemail")
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun changeEmail(it: String) {
        email.value = it
    }

    fun changeCode(it: String) {
        code.value = it
    }

    fun sendEmailAction() {
        viewModelScope.launch {
            val httpClient = TenantHttpClient(createHttpClient())
            val response = httpClient.sendCodeForUpdateEmail(EmailDTO(email.value))

            when (response) {
                is Ok -> {
                    goToSendCode()
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

    fun sendCodeAction() {
        viewModelScope.launch {
            val httpClient = TenantHttpClient(createHttpClient())
            val response = httpClient.updateEmail(code.value)

            when (response) {
                is Ok -> {
                    goToLogin()
                    snackMessage.value.showSnackbar("Email trocado com sucesso!")
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

    fun resetPage() {
        code.value = ""
        email.value = ""
        inputErrors.value = emptyMap()
        messageError.value = ""
    }
}