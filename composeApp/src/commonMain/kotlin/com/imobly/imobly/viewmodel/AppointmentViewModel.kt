package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.httpclient.AppointmentHttpClient
import com.imobly.imobly.domain.Appointment
import com.imobly.imobly.domain.Property
import kotlinx.coroutines.launch

class AppointmentViewModel(private val navController: NavController) : ViewModel() {

    val appointment = mutableStateOf(Appointment())

    val onLoadingState = mutableStateOf(false)
    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val messageError = mutableStateOf("")
    val showDialogState = mutableStateOf(false)
    val snackMessage: MutableState<SnackbarHostState> = mutableStateOf(SnackbarHostState())


    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun whenStartingThePage() {
        snackMessage.value = SnackbarHostState()
        onLoadingState.value = false
    }

    fun resetPage() {
        appointment.value = Appointment()
        inputErrors.value = emptyMap()
        messageError.value = ""
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToShowProperties() {
        navController.navigate("showproperties")
    }

    fun createAction() {
        inputErrors.value = emptyMap()
        messageError.value = ""

        println(appointment.value)

        appointment.value = appointment.value.copy(property = SharedRepository.property ?: Property())
        viewModelScope.launch {
            val httpClient = AppointmentHttpClient(createHttpClient())
            val response = httpClient.create(appointment.value)
            showDialogState.value = false
            when (response) {
                is Ok -> {
                    snackMessage.value.showSnackbar("Agendamento criado com sucesso!")
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

    fun changeMoment(it: String) {
        appointment.value = appointment.value.copy(moment = it)
    }

    fun changeGuestName(it: String) {
        appointment.value = appointment.value.copy(guestName = it)
    }

    fun changeTelephone(it: String) {
        appointment.value = appointment.value.copy(telephone = it)
    }
}