package com.imobly.imobly.ui.screens.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.input.InputDateComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.viewmodel.AppointmentViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CreateAppointmentScreen(appointmentViewModel: AppointmentViewModel) {

    val scrollState = rememberScrollState()
    appointmentViewModel.whenStartingThePage()
    appointmentViewModel.resetPage()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost(appointmentViewModel.snackMessage.value) }
    ) { paddingValues ->

        Column(
            Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TitleComp("Agendar Visita", { appointmentViewModel.goToShowProperties() }, true)

            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                InputComp(
                    label = "Visitante",
                    placeholder = "Ex: Maria Souza",
                    value = appointmentViewModel.appointment.value.guestName,
                    onValueChange = { appointmentViewModel.changeGuestName(it) },
                    isError = appointmentViewModel.inputContainsError("guestName"),
                    errorMessage = appointmentViewModel.getInputErrorMessage("guestName")
                )

                InputDateComp(
                    label = "Data e hora do encontro",
                    value = appointmentViewModel.appointment.value.moment,
                    onValueChange = { appointmentViewModel.changeMoment(it) },
                    isError = appointmentViewModel.inputContainsError("moment"),
                    errorMessage = appointmentViewModel.getInputErrorMessage("moment"),
                )

                InputComp(
                    label = "Telefone",
                    placeholder = "Ex: (00) 90000-0000",
                    value = appointmentViewModel.appointment.value.telephone,
                    onValueChange = { appointmentViewModel.changeTelephone(it) },
                    isError = appointmentViewModel.inputContainsError("telephone"),
                    errorMessage = appointmentViewModel.getInputErrorMessage("telephone")
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (appointmentViewModel.onLoadingState.value) {
                        Box(Modifier.padding(20.dp)) {
                            CircularProgressIndicator()
                        }
                    } else {
                        if (appointmentViewModel.messageError.value != "") {
                            MessageErrorComp(appointmentViewModel.messageError.value, 14.sp)
                        }
                        Box(Modifier.align(Alignment.CenterHorizontally)) {
                            ButtonComp(
                                "Agendar",
                                { Icon(Icons.Default.Create, "check") },
                                PrimaryColor,
                                { appointmentViewModel.createAction() }
                            )
                        }
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