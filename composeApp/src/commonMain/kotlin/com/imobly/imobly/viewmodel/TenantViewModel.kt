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
import com.imobly.imobly.api.httpclient.TenantHttpClient
import com.imobly.imobly.domain.Telephone
import com.imobly.imobly.domain.Tenant
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.launch


class TenantViewModel(private val navController: NavHostController): ViewModel() {

    val tenants: MutableState<List<Tenant>> = mutableStateOf(emptyList())

    val tenant = mutableStateOf(Tenant())

    val searchText: MutableState<String> = mutableStateOf("")

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
        searchText.value = ""
    }

    fun hiddenEditButton() {
        inputLockState.value = !inputLockState.value
    }
    fun changeSearchText(it: String) {
        searchText.value = it
    }

    fun inputContainsError(inputLabel: String): Boolean {
        return inputErrors.value.keys.contains(inputLabel)
    }

    fun getInputErrorMessage(inputLabel: String): String {
        return inputErrors.value[inputLabel] ?: ""
    }

    fun findAllAction() {
        viewModelScope.launch {
            val httpClient = TenantHttpClient(createHttpClient())
            tenants.value = httpClient.searchAll()
        }
    }

    fun goToEditTenant(newTenant: Tenant) {
        tenant.value = newTenant
        inputLockState.value = true
        inputErrors.value = emptyMap()
        messageError.value = ""
        selectedImages.value = emptyList()
        navController.navigate("edittenant")
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToShowTenants() {
        navController.navigate("showtenants")
    }

    fun goToCreateTenant() {
        navController.navigate("createtenant")
    }

    fun goToLogin() {
        navController.navigate("login")
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

    fun editAction() {
        viewModelScope.launch {
            onLoadingState.value = true
            val httpClient = TenantHttpClient(createHttpClient())
            if (tenant.value.id != null) {
                var imageToSend: GalleryPhotoResult? = null
                if (selectedImages.value.isNotEmpty()) {
                    imageToSend = selectedImages.value.first()
                }
                val response = httpClient.update(tenant.value.id!!, tenant.value, imageToSend)
                onLoadingState.value = false
                when (response) {
                    is Ok -> {
                        hiddenEditButton()
                        snackMessage.value.showSnackbar("Locatário editado com sucesso!")
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

    fun deleteAction() {
        viewModelScope.launch {
            val httpClient = TenantHttpClient(createHttpClient())
            if (tenant.value.id != null) {
                val response = httpClient.delete(tenant.value.id!!)

                showDialogState.value = false
                goToShowTenants()
                when (response) {
                    is Ok -> {
                        snackMessage.value.showSnackbar("Locatário deletado com sucesso!")
                    }

                    is ErrorDTO -> {
                        snackMessage.value.showSnackbar("Houve um problema ao deletar a locatário!")
                    }
                }
            }
        }
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