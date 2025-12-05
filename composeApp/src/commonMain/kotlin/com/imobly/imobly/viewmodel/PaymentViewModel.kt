package com.imobly.imobly.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.imobly.imobly.domain.Payment
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewModelScope
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.PaymentStatusDTO
import com.imobly.imobly.api.httpclient.PaymentHttpClient
import com.imobly.imobly.domain.Lease
import com.imobly.imobly.domain.MonthlyInstallment
import com.imobly.imobly.domain.enums.PaymentStatusEnum
import kotlinx.coroutines.launch

class PaymentViewModel(private val navController: NavHostController) : ViewModel() {
    val payment = mutableStateOf(Payment())

    val snackMessage = mutableStateOf(SnackbarHostState())

    val searchText = mutableStateOf("")

    val onLoadingState = mutableStateOf(false)

    val messageError = mutableStateOf("")

    private val inputErrors = mutableMapOf<String, String>()

    fun inputContainsError(field: String): Boolean =
        inputErrors[field]?.isNotEmpty() == true

    fun getInputErrorMessage(field: String): String =
        inputErrors[field] ?: ""

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToShowLeases() {
        navController.navigate("showleases")
    }
}