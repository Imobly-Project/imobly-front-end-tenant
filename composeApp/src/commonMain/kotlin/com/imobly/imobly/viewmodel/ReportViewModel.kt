package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.dto.CreateReportDTO
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.httpclient.PropertyHttpClient
import com.imobly.imobly.api.httpclient.ReportHttpClient
import com.imobly.imobly.domain.Property
import com.imobly.imobly.domain.Report
import com.imobly.imobly.domain.enums.StatusReportEnum
import com.imobly.imobly.domain.Tenant
import kotlinx.coroutines.launch

class ReportViewModel(private val navController: NavController): ViewModel() {

    val tenant = mutableStateOf(Tenant())
    val tenantProperties = mutableStateOf<List<Property>>(emptyList())
    val tenantPropertySelected = mutableStateOf(Property())

    val report = mutableStateOf(Report(
        tenant = tenant.value
    ))
    val inputLockState = mutableStateOf(true)
    val onLoadingState = mutableStateOf(false)
    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val messageError = mutableStateOf("")
    val showDialogState = mutableStateOf(false)
    val searchText: MutableState<String> = mutableStateOf("")
    val reports: MutableState<List<Report>> = mutableStateOf(emptyList())

    val snackMessage: MutableState<SnackbarHostState> = mutableStateOf(SnackbarHostState())


    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun changeShowDialog() {
        showDialogState.value = !showDialogState.value
    }

    fun hiddenEditButton() {
        inputLockState.value = !inputLockState.value
    }

    fun whenStartingThePage() {
        snackMessage.value = SnackbarHostState()
        onLoadingState.value = false
    }

    fun resetPage() {
        tenant.value = Tenant()
        report.value = Report(tenant= tenant.value)
        inputErrors.value = emptyMap()
        messageError.value = ""
    }

    fun changeSearchText(it: String) {
        searchText.value = it
    }

    fun findAllAction() {
        viewModelScope.launch {
            val httpClient = ReportHttpClient(createHttpClient())
            reports.value = httpClient.searchAByProfileAndTitleOrMessage()
        }
    }

    fun goToCreateReport() {
        inputErrors.value = emptyMap()
        messageError.value = ""
        navController.navigate("createreport")
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToShowReports() {
        navController.navigate("showreports")
    }

    fun searchAction() {
        viewModelScope.launch {
            val reportHttpClient = ReportHttpClient(createHttpClient())
            val list = reportHttpClient.searchAByProfileAndTitleOrMessage(searchText.value)
            reports.value = list

            val propertyHttpClient = PropertyHttpClient(createHttpClient())
            tenantProperties.value = propertyHttpClient.searchAllByTitle()

        }
    }

    fun createReportAction() {
        viewModelScope.launch {
            val httpClient = ReportHttpClient(createHttpClient())
            onLoadingState.value = true
            val response = httpClient.createReport(CreateReportDTO(report.value.title, report.value.message, tenantPropertySelected.value.id ?: ""))
            onLoadingState.value = false
            when (response) {
                is Ok -> {
                    hiddenEditButton()
                    inputErrors.value = emptyMap()
                    messageError.value = ""
                    snackMessage.value.showSnackbar("Reportação feita!")
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

    fun statusReportOptions(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        StatusReportEnum.entries.forEach {
            map[it.label] = it.name
        }
        return map
    }

    fun changeTitle(it: String) {
        report.value = report.value.copy(title = it)
    }

    fun changeMessage(it: String) {
        report.value = report.value.copy(message = it)
    }

    fun propertiesOptions(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        tenantProperties.value.forEach {
            map[it.title] = it.id ?: ""
        }
        return map
    }

    fun changeProperty(property: Property) {
        tenantPropertySelected.value = property
    }
}