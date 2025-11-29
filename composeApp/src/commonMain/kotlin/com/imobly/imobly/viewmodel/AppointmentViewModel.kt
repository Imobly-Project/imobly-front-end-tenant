package com.imobly.imobly.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppointmentViewModel(
    private val navController: NavController
) : ViewModel() {

    // Snackbar host state para usar em Scaffold
    val snackbarState = SnackbarHostState()

    // Estados expostos como MutableState para facilitar uso na UI (viewModel.scheduledDate.value)
    val scheduledDate: MutableState<String> = mutableStateOf("2025-01-01")
    val scheduledTime: MutableState<String> = mutableStateOf("10:00")

    val guide: MutableState<String> = mutableStateOf("")
    val guest: MutableState<String> = mutableStateOf("")
    val selectedProperty: MutableState<String> = mutableStateOf("")

    // Lista de propriedades (simulada)
    private val _properties = mutableStateListOf("Casa 1", "Apto 12A", "Cobertura 505")
    val tenantProperties = _properties

    // Mensagens / loading
    val messageError = mutableStateOf("")
    val loading = mutableStateOf(false)

    // Simula carregamento inicial (ex.: buscar propriedades)
    fun whenStartingThePage() {
        // se quiser buscar do backend, substitua por chamada real
        // aqui já temos _properties preenchido, apenas seleciona a primeira
        if (tenantProperties.isNotEmpty()) {
            selectedProperty.value = tenantProperties.first()
        }
    }

    // Opções para dropdowns (datas e horários simples; adapte conforme precisar)
    fun dateOptions(): List<String> {
        // Em produção: gerar datas dinâmicas
        return listOf("2025-01-01", "2025-01-02", "2025-01-03", "2025-01-04")
    }

    fun timeOptions(): List<String> {
        return listOf("09:00", "10:00", "11:00", "14:00", "15:00", "16:00")
    }

    fun propertiesOptions(): List<String> {
        return tenantProperties.toList()
    }

    // Mutators
    fun changeDate(value: String) { scheduledDate.value = value }
    fun changeTime(value: String) { scheduledTime.value = value }
    fun changeGuide(value: String) { guide.value = value }
    fun changeGuest(value: String) { guest.value = value }
    fun changeProperty(value: String) { selectedProperty.value = value }

    fun createAppointmentAction() {
        // validação simples
        if (guide.value.isBlank() || guest.value.isBlank() || selectedProperty.value.isBlank()) {
            messageError.value = "Preencha todos os campos."
            return
        }

        // simula envio assíncrono usando viewModelScope
        loading.value = true
        viewModelScope.launch {
            // simula delay de rede
            delay(1200)
            loading.value = false
            // fecha a tela
            navController.popBackStack()
        }
    }

    fun goBack() {
        navController.popBackStack()
    }
}