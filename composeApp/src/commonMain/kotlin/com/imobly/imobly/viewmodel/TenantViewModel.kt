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
import com.imobly.imobly.api.dto.UpdateProfileDTO
import com.imobly.imobly.api.httpclient.AuthenticationHttpClient
import com.imobly.imobly.api.httpclient.TenantHttpClient
import com.imobly.imobly.domain.Telephone
import com.imobly.imobly.domain.Tenant
import com.imobly.imobly.domain.enums.MaritalStatusEnum
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.launch

class TenantViewModel(private val navController: NavHostController): ViewModel() {

    val tenant = mutableStateOf(Tenant())

    val selectedImages = mutableStateOf(emptyList<GalleryPhotoResult>())

    val inputLockState = mutableStateOf(true)

    val onLoadingState = mutableStateOf(false)

    val passwordVisibilityState = mutableStateOf(false)

    val inputErrors = mutableStateOf(emptyMap<String, String>())
    val messageError = mutableStateOf("")

    val showDialogState = mutableStateOf(false)

    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

    fun changePasswordVisibility() {
        passwordVisibilityState.value = !passwordVisibilityState.value
    }

    fun changeShowDialog() {
        showDialogState.value = !showDialogState.value
    }

    fun whenStartingThePage() {
        snackMessage.value = SnackbarHostState()
        onLoadingState.value = false
    }

    fun resetPage() {
        tenant.value = Tenant()
        selectedImages.value = emptyList()
        inputErrors.value = emptyMap()
        messageError.value = ""
    }

    fun hiddenEditButton() {
        inputLockState.value = !inputLockState.value
    }


    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun findProfileAction() {
        viewModelScope.launch {
            val httpClient = TenantHttpClient(createHttpClient())
            tenant.value = httpClient.findByProfile()
        }
    }

    fun goToHome() {
        navController.navigate("home")
    }


    fun signUpAction() {
        if (selectedImages.value.isNotEmpty()) {
            viewModelScope.launch {
                onLoadingState.value = true
                val httpClient = AuthenticationHttpClient(createHttpClient())
                var imageToSend: GalleryPhotoResult? = null
                if (selectedImages.value.isNotEmpty()) {
                    imageToSend = selectedImages.value.first()
                }
                val response = httpClient.signUp(tenant.value, imageToSend)
                onLoadingState.value = false
                when (response) {
                    is Ok -> {
                        tenant.value = Tenant()
                        selectedImages.value = emptyList()
                        inputErrors.value = emptyMap()
                        messageError.value = ""
                        snackMessage.value.showSnackbar("Locatário salvo com sucesso!")
                    }

                    is ErrorDTO -> {
                        val errors = mutableMapOf<String, String>()
                        response.errorFields?.forEach { errors[it.name] = it.description }
                        inputErrors.value = errors
                        messageError.value = response.message
                    }

                }
            }
        } else {
            messageError.value = "Você deve enviar uma imagem"
        }
    }

    fun goToLogin() {
        navController.navigate("login")
    }

    fun editAction() {
        val dto = UpdateProfileDTO(
            tenant.value.email,
            tenant.value.telephones
        )

        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = TenantHttpClient(createHttpClient())
            var imageToSend: GalleryPhotoResult? = null
            if (selectedImages.value.isNotEmpty()) {
                imageToSend = selectedImages.value.first()
            }
            val response = httpClient.updateProfile(dto, imageToSend)
            onLoadingState.value = false
            when (response) {
                is Ok -> {
                    hiddenEditButton()
                    snackMessage.value.showSnackbar("Sua conta foi editada com sucesso!")
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

    fun deleteAction() {
        viewModelScope.launch {
            val httpClient = TenantHttpClient(createHttpClient())
            val response = httpClient.deleteProfile()

            showDialogState.value = false
            when (response) {
                is Ok -> {
                    goToLogin()
                    snackMessage.value.showSnackbar("Locatário deletado com sucesso!")
                }

                is ErrorDTO -> {
                    messageError.value = response.message
                }
            }
        }
    }

    fun maritalStatusOptions(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        MaritalStatusEnum.entries.forEach {
            map[it.label] = it.name
        }
        return map
    }

    fun changeFirstName(it: String) {
        tenant.value = tenant.value.copy(firstName = it)
    }

    fun changeLastName(it: String) {
        tenant.value = tenant.value.copy(lastName = it)
    }

    fun changeEmail(it: String) {
        tenant.value = tenant.value.copy(email = it)
    }

    fun changeTelephoneOne(it: String) {
        tenant.value = tenant.value.copy(telephones = Telephone(
            it,
            tenant.value.telephones.telephone2,
            tenant.value.telephones.telephone3
        ))
    }

    fun changeTelephoneTwo(it: String) {
        tenant.value = tenant.value.copy(telephones = Telephone(
            tenant.value.telephones.telephone1,
            it,
            tenant.value.telephones.telephone3
        ))
    }

    fun changeTelephoneThree(it: String) {
        tenant.value = tenant.value.copy(telephones = Telephone(
            tenant.value.telephones.telephone1,
            tenant.value.telephones.telephone2,
            it
        ))
    }

    fun changePassword(it: String) {
        tenant.value = tenant.value.copy(password = it)
    }

    fun changeNationality(it: String) {
        tenant.value = tenant.value.copy(nationality = it)
    }

    fun changeJob(it: String) {
        tenant.value = tenant.value.copy(job = it)
    }

    fun changeCpf(it: String) {
        tenant.value = tenant.value.copy(cpf = it)
    }

    fun changeRg(it: String) {
        tenant.value = tenant.value.copy(rg = it)
    }

    fun changeBirthDate(it: String) {
        tenant.value = tenant.value.copy(birthDate = it)
    }

    fun changeCep(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(cep = it)
        )
    }

    fun changeState(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(state = it)
        )
    }

    fun changeCity(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(city = it)
        )
    }

    fun changeNeighborhood(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(neighborhood = it)
        )
    }

    fun changeStreet(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(street = it)
        )
    }

    fun changeNumber(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(
                number = if (it.toIntOrNull() != null || it == "") {
                    it
                } else {
                    tenant.value.address.number
                }
            )
        )
    }

    fun changeComplement(it: String) {
        tenant.value = tenant.value.copy(
            address = tenant.value.address.copy(complement = it)
        )
    }
}