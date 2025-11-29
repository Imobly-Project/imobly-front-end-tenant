package com.imobly.imobly.ui.screens.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.input.InputDropdownComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.viewmodel.AppointmentViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CreateAppointmentScreen(viewModel: AppointmentViewModel) {

    val scrollState = rememberScrollState()
    viewModel.whenStartingThePage()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost(viewModel.snackbarState) }
    ) { paddingValues ->

        Column(
            Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TitleComp("Agendar Visita", { viewModel.goBack() }, true)

            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Data
                InputDropdownComp(
                    label = "Data",
                    options = viewModel.dateOptions().associateWith { it }, // Map<String,String>
                    selectedOption = viewModel.scheduledDate.value,
                    onOptionSelected = { viewModel.changeDate(it) }
                )

                // Horário
                InputDropdownComp(
                    label = "Hora",
                    options = viewModel.timeOptions().associateWith { it },
                    selectedOption = viewModel.scheduledTime.value,
                    onOptionSelected = { viewModel.changeTime(it) }
                )

                InputComp(
                    label = "Guia responsável",
                    placeholder = "Ex: José Silva",
                    value = viewModel.guide.value,
                    onValueChange = { viewModel.changeGuide(it) },
                )

                InputComp(
                    label = "Visitante",
                    placeholder = "Ex: Maria Souza",
                    value = viewModel.guest.value,
                    onValueChange = { viewModel.changeGuest(it) },
                )

                InputDropdownComp(
                    label = "Propriedade",
                    options = viewModel.propertiesOptions().associateWith { it },
                    selectedOption = viewModel.selectedProperty.value,
                    onOptionSelected = { viewModel.changeProperty(it) }
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (viewModel.messageError.value.isNotBlank()) {
                        MessageErrorComp(viewModel.messageError.value, 14.sp)
                    }

                    if (viewModel.loading.value) {
                        Box(Modifier.padding(20.dp)) {
                            CircularProgressIndicator()
                        }
                    } else {
                        ButtonComp(
                            "Agendar visita",
                            { Icon(Icons.Default.Check, "confirmar") },
                            ConfirmColor,
                            { viewModel.createAppointmentAction() },
                            155.dp,
                            16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewAppointment() {
    CreateAppointmentScreen(AppointmentViewModel(rememberNavController()))
}