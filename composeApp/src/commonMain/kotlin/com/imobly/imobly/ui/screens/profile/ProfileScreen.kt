package com.imobly.imobly.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.enums.MaritalStatusEnum
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.confirmdialog.ConfirmDialogComp
import com.imobly.imobly.ui.components.imagepicker.ImagePickerComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.input.InputDateComp
import com.imobly.imobly.ui.components.input.InputDropdownComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.TenantViewModel
import io.github.ismoy.imagepickerkmp.domain.extensions.loadPainter
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen(tenantViewModel: TenantViewModel) {
    val scrollState = rememberScrollState()

    tenantViewModel.whenStartingThePage()
    tenantViewModel.resetPage()
    tenantViewModel.findProfileAction()

    Scaffold(
        topBar = {
            TopBarComp()
        },
        snackbarHost = { SnackbarHost( tenantViewModel.snackMessage.value ) },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Column(
            Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleComp("Perfil do cliente",{
                tenantViewModel.goToHome()
            }, true)

            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (tenantViewModel.selectedImages.value.isEmpty()) {
                    KamelImage(
                        resource = { asyncPainterResource(tenantViewModel.tenant.value.pathImage) },
                        contentDescription = "Imagem do locatário ${tenantViewModel.tenant.value.firstName}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .border(4.dp, Color(0xFFE35336), CircleShape),
                        onLoading = { progress -> CircularProgressIndicator({ progress }) },
                        onFailure = { CircularProgressIndicator() }
                    )
                } else {
                    tenantViewModel.selectedImages.value.first().loadPainter()?.let {
                        Image(
                            painter = it,
                            contentDescription = "Imagem do locatário selecionada",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape)
                                .border(4.dp, Color(0xFFE35336), CircleShape),
                        )
                    }
                }

                if (!tenantViewModel.inputLockState.value) {
                    ImagePickerComp("Escolha as imagens", tenantViewModel.selectedImages)
                }

                InputComp(
                    label = "Nome",
                    placeholder = "João",
                    value = tenantViewModel.tenant.value.firstName,
                    onValueChange = { tenantViewModel.changeFirstName(it) },
                    readOnly = true,
                    isError = tenantViewModel.inputContainsError("firstName"),
                    errorMessage = tenantViewModel.getInputErrorMessage("firstName")
                )

                InputComp(
                    label = "Sobrenome",
                    placeholder = "Silva",
                    value = tenantViewModel.tenant.value.lastName,
                    onValueChange = { tenantViewModel.changeLastName(it) },
                    readOnly = true,
                    isError = tenantViewModel.inputContainsError("lastName"),
                    errorMessage = tenantViewModel.getInputErrorMessage("lastName")
                )

                InputComp(
                    label = "Email",
                    placeholder = "joao@dominio.com",
                    value = tenantViewModel.tenant.value.email,
                    onValueChange = { tenantViewModel.changeEmail(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("email"),
                    errorMessage = tenantViewModel.getInputErrorMessage("email")
                )

                InputComp(
                    label = "Telefone 1",
                    placeholder = "Ex: (00) 00000-0000",
                    value = tenantViewModel.tenant.value.telephones.telephone1,
                    onValueChange = { tenantViewModel.changeTelephoneOne(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = (tenantViewModel.inputContainsError("telephones") ||
                            tenantViewModel.inputContainsError("telephones.telephone1")),
                    errorMessage = if (tenantViewModel.inputContainsError("telephones")) {
                        tenantViewModel.getInputErrorMessage("telephones")
                    } else {
                        tenantViewModel.getInputErrorMessage("telephones.telephone1")
                    }
                )

                InputComp(
                    label = "Telefone 2",
                    placeholder = "Ex: (00) 00000-0000",
                    value = tenantViewModel.tenant.value.telephones.telephone2 ?: "",
                    onValueChange = { tenantViewModel.changeTelephoneTwo(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("telephones.telephone2"),
                    errorMessage = tenantViewModel.getInputErrorMessage("telephones.telephone2")
                )

                InputComp(
                    label = "Telefone 3",
                    placeholder = "Ex: (00) 00000-0000",
                    value = tenantViewModel.tenant.value.telephones.telephone3 ?: "",
                    onValueChange = { tenantViewModel.changeTelephoneThree(it) },
                    readOnly = tenantViewModel.inputLockState.value,
                    isError = tenantViewModel.inputContainsError("telephones.telephone3"),
                    errorMessage = tenantViewModel.getInputErrorMessage("telephones.telephone3")
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InputComp(
                        label = "RG",
                        placeholder = "Ex: 00.000.000-0",
                        value = tenantViewModel.tenant.value.rg,
                        onValueChange = { tenantViewModel.changeRg(it) },
                        isNumeric = true,
                        readOnly = true,
                        isError = tenantViewModel.inputContainsError("rg"),
                        errorMessage = tenantViewModel.getInputErrorMessage("rg"),
                        modifier = Modifier.weight(1f)
                    )
                    InputComp(
                        label = "CPF",
                        placeholder = "Ex: 000.000.000-00",
                        value = tenantViewModel.tenant.value.cpf,
                        onValueChange = { tenantViewModel.changeCpf(it) },
                        isNumeric = true,
                        readOnly = true,
                        isError = tenantViewModel.inputContainsError("cpf"),
                        errorMessage = tenantViewModel.getInputErrorMessage("cpf"),
                        modifier = Modifier.weight(1f)
                    )
                }

                InputComp(
                    label = "Trabalho",
                    placeholder = "Ex: Pedreiro",
                    value = tenantViewModel.tenant.value.job,
                    onValueChange = { tenantViewModel.changeJob(it) },
                    readOnly = true,
                    isError = tenantViewModel.inputContainsError("job"),
                    errorMessage = tenantViewModel.getInputErrorMessage("job"),
                )

                InputComp(
                    label = "Nacionalidade",
                    placeholder = "Brasileiro",
                    value = tenantViewModel.tenant.value.nationality,
                    onValueChange = { tenantViewModel.changeNationality(it) },
                    readOnly = true,
                    isError = tenantViewModel.inputContainsError("nationality"),
                    errorMessage = tenantViewModel.getInputErrorMessage("nationality")
                )

                InputDropdownComp(
                    label = "Estado Civil",
                    options = tenantViewModel.maritalStatusOptions(),
                    selectedOption = tenantViewModel.tenant.value.maritalStatus.label,
                    onOptionSelected = { selectedLabel->
                        tenantViewModel.tenant.value =
                            tenantViewModel.tenant.value.copy(
                                maritalStatus = MaritalStatusEnum.entries.first{ it.name == selectedLabel}) },
                    isEnabled = false
                )

                InputDateComp(
                    "Data de nascimento",
                    value = tenantViewModel.tenant.value.birthDate,
                    onValueChange = { tenantViewModel.changeBirthDate(it) },
                    isError = tenantViewModel.inputContainsError("birthDate"),
                    errorMessage = tenantViewModel.getInputErrorMessage("birthDate"),
                    readOnly = true
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InputComp(
                        label = "CEP",
                        placeholder = "Ex: 01001-000",
                        value = tenantViewModel.tenant.value.address.cep,
                        onValueChange = { tenantViewModel.changeCep(it) },
                        isNumeric = true,
                        readOnly = true,
                        isError = tenantViewModel.inputContainsError("address.cep"),
                        errorMessage = tenantViewModel.getInputErrorMessage("address.cep"),
                        modifier = Modifier.weight(1f)
                    )

                    InputComp(
                        label = "Estado",
                        placeholder = "Ex: SP",
                        value = tenantViewModel.tenant.value.address.state,
                        onValueChange = { tenantViewModel.changeState(it) },
                        readOnly = true,
                        isError = tenantViewModel.inputContainsError("address.state"),
                        errorMessage = tenantViewModel.getInputErrorMessage("address.state"),
                        modifier = Modifier.weight(1f)
                    )
                }
                InputComp(
                    label = "Cidade",
                    placeholder = "Ex: Guarulhos",
                    value = tenantViewModel.tenant.value.address.city,
                    onValueChange = { tenantViewModel.changeCity(it) },
                    readOnly = true,
                    isError = tenantViewModel.inputContainsError("address.city"),
                    errorMessage = tenantViewModel.getInputErrorMessage("address.city")
                )

                InputComp(
                    label = "Bairro",
                    placeholder = "Ex: Campo verde",
                    value = tenantViewModel.tenant.value.address.neighborhood,
                    onValueChange = { tenantViewModel.changeNeighborhood(it) },
                    readOnly = true,
                    isError = tenantViewModel.inputContainsError("address.neighborhood"),
                    errorMessage = tenantViewModel.getInputErrorMessage("address.neighborhood")
                )

                InputComp(
                    label = "Rua",
                    placeholder = "Ex: Olinda Alves",
                    value = tenantViewModel.tenant.value.address.street,
                    onValueChange = { tenantViewModel.changeStreet(it) },
                    readOnly = true,
                    isError = tenantViewModel.inputContainsError("address.street"),
                    errorMessage = tenantViewModel.getInputErrorMessage("address.street")
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    InputComp(
                        label = "Número",
                        placeholder = "Ex: 68",
                        value = tenantViewModel.tenant.value.address.number,
                        onValueChange = { tenantViewModel.changeNumber(it) },
                        isNumeric = true,
                        readOnly = true,
                        isError = tenantViewModel.inputContainsError("address.number"),
                        errorMessage = tenantViewModel.getInputErrorMessage("address.number"),
                        modifier = Modifier.weight(1f)
                    )

                    InputComp(
                        label = "Complemento",
                        placeholder = "Ex: Bloco 18",
                        value = tenantViewModel.tenant.value.address.complement,
                        onValueChange = { tenantViewModel.changeComplement(it) },
                        readOnly = true,
                        isError = tenantViewModel.inputContainsError("address.complement"),
                        errorMessage = tenantViewModel.getInputErrorMessage("address.complement"),
                        modifier = Modifier.weight(1f)
                    )
                }

                ConfirmDialogComp(
                    tenantViewModel.showDialogState.value,
                    "Está ação deletará sua conta",
                    "Tem certeza que deseja continuar? Está ação é irreversível.",
                    { tenantViewModel.deleteAction() },
                    { tenantViewModel.changeShowDialog() }
                )


                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (tenantViewModel.messageError.value != "") {
                        MessageErrorComp(tenantViewModel.messageError.value, 16.sp)
                    }
                    if (tenantViewModel.inputLockState.value) {

                        FlowRow {

                            ButtonComp(
                                "Editar dados",
                                { Icon(Icons.Default.Edit, "editar") },
                                PrimaryColor,
                                { tenantViewModel.hiddenEditButton() },
                                185.dp,
                                16.sp
                            )

                            ButtonComp(
                                "Excluir",
                                { Icon(Icons.Default.Delete, "deletar") },
                                CancelColor,
                                { tenantViewModel.changeShowDialog() },
                                140.dp,
                                16.sp
                            )

                        }

                    } else {
                        if (tenantViewModel.onLoadingState.value) {
                            Box(Modifier.padding(20.dp)) {
                                CircularProgressIndicator()
                            }
                        } else {
                            FlowRow {

                                ButtonComp(
                                    "Salvar",
                                    { Icon(Icons.Default.Check, "confirmar") },
                                    ConfirmColor,
                                    { tenantViewModel.editAction() },
                                    140.dp,
                                    16.sp
                                )

                                ButtonComp(
                                    "Cancelar",
                                    { Icon(Icons.Default.Cancel, "cancelar") },
                                    CancelColor,
                                    { tenantViewModel.hiddenEditButton() },
                                    155.dp,
                                    16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    val navControllerFake = rememberNavController()
    ProfileScreen(TenantViewModel(navControllerFake))
}