package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.dto.EmailDTO
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResetPasswordDTO
import com.imobly.imobly.api.httpclient.PasswordRecoveryHttpClient
import kotlinx.coroutines.launch

class ResetPasswordViewModel(private val navController: NavHostController) : ViewModel() {

    val email = mutableStateOf("")
    val newPassword = mutableStateOf("")
    val newPasswordConfirmation = mutableStateOf("")
    val code = mutableStateOf("")

    val passwordVisibilityState = mutableStateOf(false)
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

    fun changePasswordVisibility() {
        passwordVisibilityState.value = !passwordVisibilityState.value
    }

    fun goToLogin() {
        navController.navigate("login")
    }

    fun goToInsertCode() {
        navController.navigate("insertcode")
    }

    fun goToChangePassword() {
        navController.navigate("changepassword")
    }

    fun changeEmail(it: String) {
        email.value = it
    }

    fun changeCode(it: String) {
        code.value = it
    }

    fun changeNewPassword(it: String) {
        newPassword.value = it
    }

    fun changeNewPasswordConfirmation(it: String) {
        newPasswordConfirmation.value = it
    }

    fun sendRequestByEmailAction() {
        messageError.value = ""

        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = PasswordRecoveryHttpClient(createHttpClient())
            val response = httpClient.requestCode(EmailDTO(email.value))
            onLoadingState.value = false
            when (response) {
                is Ok -> {
                    goToInsertCode()
                    snackMessage.value.showSnackbar("Código enviado com sucesso!")
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

    fun validateCodeAction() {
        messageError.value = ""

        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = PasswordRecoveryHttpClient(createHttpClient())
            val response = httpClient.validateCode(email.value, code.value)
            onLoadingState.value = false
            when (response) {
                is Ok -> {
                    goToChangePassword()
                    snackMessage.value.showSnackbar("Código validado com sucesso!")
                }

                is ErrorDTO -> {
                    code.value = ""
                    val errors = mutableMapOf<String, String>()
                    response.errorFields?.forEach { errors[it.name] = it.description }
                    inputErrors.value = errors
                    messageError.value = response.message
                }
            }
        }
    }

    fun resetPasswordAction() {
        if (newPassword.value != newPasswordConfirmation.value) {
            messageError.value = "As senhas não são iguais"
            return
        }
        messageError.value = ""

        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = PasswordRecoveryHttpClient(createHttpClient())
            val response = httpClient.resetPassword(ResetPasswordDTO(email.value, code.value, newPassword.value))
            onLoadingState.value = false
            when (response) {
                is Ok -> {
                    goToLogin()
                    snackMessage.value.showSnackbar("Senha trocada com sucesso!")
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
}