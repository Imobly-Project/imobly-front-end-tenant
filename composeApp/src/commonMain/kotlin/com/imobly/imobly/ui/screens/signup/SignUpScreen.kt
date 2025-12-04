package com.imobly.imobly.ui.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.enums.MaritalStatusEnum
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.imagepicker.ImagePickerComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.input.InputDateComp
import com.imobly.imobly.ui.components.input.InputDropdownComp
import com.imobly.imobly.ui.components.input.InputPasswordComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.TenantViewModel
import imobly_front_end_tenant.composeapp.generated.resources.Res
import imobly_front_end_tenant.composeapp.generated.resources.image_logo_white
import io.github.ismoy.imagepickerkmp.domain.extensions.loadPainter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun SignUpScreen(tenantViewModel: TenantViewModel) {
    val scrollState = rememberScrollState()

    tenantViewModel.whenStartingThePage()

    MaterialTheme {
        Scaffold(
            topBar = {},
            snackbarHost = {
                SnackbarHost(tenantViewModel.snackMessage.value)
            },
            contentWindowInsets = WindowInsets.systemBars
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .background(Color(242, 96, 63))
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.image_logo_white),
                        contentDescription = "Logo do Imobly",
                        modifier = Modifier.size(160.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .padding(paddingValues = PaddingValues(0.dp, 160.dp, 0.dp, 0.dp))
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(50.dp, 50.dp)
                        )
                        .clip(RoundedCornerShape(50.dp, 50.dp)),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(40.dp))
                    TitleComp("Cadastrar-se", { tenantViewModel.goToLogin() }, true, 30.sp)
                    Spacer(Modifier.size(20.dp))

                    Column(
                        Modifier
                            .widthIn(max = 1000.dp)
                            .fillMaxWidth(0.9f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (tenantViewModel.selectedImages.value.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(CircleShape)
                                    .border(4.dp, Color(0xFFE35336), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                tenantViewModel.selectedImages.value.first().loadPainter()?.let {
                                    Image(
                                        painter = it,
                                        contentDescription = "imagem do ${tenantViewModel.tenant.value.firstName}",
                                        contentScale = ContentScale.Crop,
                                    )
                                }
                            }
                        }

                        ImagePickerComp("Escolha as imagens", tenantViewModel.selectedImages, false)

                        InputComp(
                            label = "Nome",
                            placeholder = "Ex: João",
                            value = tenantViewModel.tenant.value.firstName,
                            onValueChange = { tenantViewModel.changeFirstName(it) },
                            isError = tenantViewModel.inputContainsError("firstName"),
                            errorMessage = tenantViewModel.getInputErrorMessage("firstName")
                        )

                        InputComp(
                            label = "Sobrenome",
                            placeholder = "Ex: Silva",
                            value = tenantViewModel.tenant.value.lastName,
                            onValueChange = { tenantViewModel.changeLastName(it) },
                            isError = tenantViewModel.inputContainsError("lastName"),
                            errorMessage = tenantViewModel.getInputErrorMessage("lastName")
                        )

                        InputComp(
                            label = "Email",
                            placeholder = "Ex: joao@dominio.com",
                            value = tenantViewModel.tenant.value.email,
                            onValueChange = { tenantViewModel.changeEmail(it) },
                            isError = tenantViewModel.inputContainsError("email"),
                            errorMessage = tenantViewModel.getInputErrorMessage("email")
                        )

                        InputPasswordComp(
                            label = "Senha",
                            placeholder = "Ex: ********",
                            value = tenantViewModel.tenant.value.password,
                            onValueChange = { tenantViewModel.changePassword(it) },
                            isError = tenantViewModel.inputContainsError("password"),
                            errorMessage = tenantViewModel.getInputErrorMessage("password"),
                            passwordVisible = tenantViewModel.passwordVisibilityState.value,
                            changePasswordVisible = { tenantViewModel.changePasswordVisibility() }
                        )

                        InputComp(
                            label = "Telefone 1",
                            placeholder = "Ex: (00) 00000-0000",
                            value = tenantViewModel.tenant.value.telephones.telephone1,
                            onValueChange = { tenantViewModel.changeTelephoneOne(it) },
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
                            isError = tenantViewModel.inputContainsError("telephones.telephone2"),
                            errorMessage = tenantViewModel.getInputErrorMessage("telephones.telephone2")
                        )

                        InputComp(
                            label = "Telefone 3",
                            placeholder = "Ex: (00) 00000-0000",
                            value = tenantViewModel.tenant.value.telephones.telephone3 ?: "",
                            onValueChange = { tenantViewModel.changeTelephoneThree(it) },
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
                                isError = tenantViewModel.inputContainsError("rg"),
                                errorMessage = tenantViewModel.getInputErrorMessage("rg"),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(Modifier.size(10.dp))
                            InputComp(
                                label = "CPF",
                                placeholder = "Ex: 000.000.000-00",
                                value = tenantViewModel.tenant.value.cpf,
                                onValueChange = { tenantViewModel.changeCpf(it) },
                                isNumeric = true,
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
                            isError = tenantViewModel.inputContainsError("job"),
                            errorMessage = tenantViewModel.getInputErrorMessage("job"),
                        )

                        InputComp(
                            label = "Nacionalidade",
                            placeholder = "Ex: Brasileiro",
                            value = tenantViewModel.tenant.value.nationality,
                            onValueChange = { tenantViewModel.changeNationality(it) },
                            isError = tenantViewModel.inputContainsError("nationality"),
                            errorMessage = tenantViewModel.getInputErrorMessage("nationality"),
                        )

                        InputDropdownComp(
                            label = "Estado Civil",
                            options = tenantViewModel.maritalStatusOptions(),
                            selectedOption = tenantViewModel.tenant.value.maritalStatus.label,
                            onOptionSelected = { selectedLabel ->
                                tenantViewModel.tenant.value =
                                    tenantViewModel.tenant.value.copy(
                                        maritalStatus = MaritalStatusEnum.entries.first { it.name == selectedLabel })
                            },
                        )

                        InputDateComp(
                            "Data de nascimento",
                            value = tenantViewModel.tenant.value.birthDate,
                            onValueChange = { tenantViewModel.changeBirthDate(it) },
                            isError = tenantViewModel.inputContainsError("birthDate"),
                            errorMessage = tenantViewModel.getInputErrorMessage("birthDate"),
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
                                isError = tenantViewModel.inputContainsError("address.cep"),
                                errorMessage = tenantViewModel.getInputErrorMessage("address.cep"),
                                modifier = Modifier.weight(1f)
                            )

                            InputComp(
                                label = "Estado",
                                placeholder = "Ex: SP",
                                value = tenantViewModel.tenant.value.address.state,
                                onValueChange = { tenantViewModel.changeState(it) },
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
                            isError = tenantViewModel.inputContainsError("address.city"),
                            errorMessage = tenantViewModel.getInputErrorMessage("address.city")
                        )

                        InputComp(
                            label = "Bairro",
                            placeholder = "Ex: Campo verde",
                            value = tenantViewModel.tenant.value.address.neighborhood,
                            onValueChange = { tenantViewModel.changeNeighborhood(it) },
                            isError = tenantViewModel.inputContainsError("address.neighborhood"),
                            errorMessage = tenantViewModel.getInputErrorMessage("address.neighborhood")
                        )

                        InputComp(
                            label = "Rua",
                            placeholder = "Ex: Olinda Alves",
                            value = tenantViewModel.tenant.value.address.street,
                            onValueChange = { tenantViewModel.changeStreet(it) },
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
                                isError = tenantViewModel.inputContainsError("address.number"),
                                errorMessage = tenantViewModel.getInputErrorMessage("address.number"),
                                modifier = Modifier.weight(1f)
                            )

                            InputComp(
                                label = "Complemento",
                                placeholder = "Ex: Bloco 18",
                                value = tenantViewModel.tenant.value.address.complement,
                                onValueChange = { tenantViewModel.changeComplement(it) },
                                isError = tenantViewModel.inputContainsError("address.complement"),
                                errorMessage = tenantViewModel.getInputErrorMessage("address.complement"),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (tenantViewModel.onLoadingState.value) {
                                Box(Modifier.padding(20.dp)) {
                                    CircularProgressIndicator()
                                }
                            } else {
                                if (tenantViewModel.messageError.value != "") {
                                    MessageErrorComp(tenantViewModel.messageError.value, 14.sp)
                                }
                                Box(Modifier.align(Alignment.CenterHorizontally)) {
                                    ButtonComp(
                                        "Cadastrar Locatário",
                                        { Icon(Icons.Default.Create, "check") },
                                        PrimaryColor,
                                        { tenantViewModel.signUpAction() }
                                    )
                                }
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize().padding(vertical = 50.dp)
                        ) {

                            Text(
                                "Já possui conta? ",
                                textAlign = TextAlign.Center,
                                fontFamily = montserratFont(),
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                "Logue-se",
                                textAlign = TextAlign.Center,
                                fontFamily = montserratFont(),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.clickable { tenantViewModel.goToLogin() }
                            )

                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    val navControllerFake = rememberNavController()
    SignUpScreen(TenantViewModel(navControllerFake))
}
