package com.imobly.imobly.ui.screens.createreport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import com.imobly.imobly.viewmodel.ReportViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CreateReportScreen(reportViewModel: ReportViewModel) {
    val scrollState = rememberScrollState()

    reportViewModel.whenStartingThePage()

    Scaffold(
        topBar = {
            TopBarComp()
        },
        snackbarHost = { SnackbarHost( reportViewModel.snackMessage.value ) },
        contentWindowInsets = WindowInsets.systemBars
    ){
            paddingValues ->
        Column (
            Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            TitleComp("Reportar problema", { reportViewModel.goToShowReports() }, true)

            Column (
                Modifier
                    .verticalScroll(scrollState)
                    .widthIn(max = 1000.dp)
                    .fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                InputComp(
                    label = "Título",
                    placeholder = "Ex: Goteira no quarto",
                    value = reportViewModel.report.value.title,
                    onValueChange = { reportViewModel.changeTitle(it) },
                    isError = reportViewModel.inputContainsError("title"),
                    errorMessage = reportViewModel.getInputErrorMessage("title")
                )

                InputComp(
                    label = "Mensagem",
                    placeholder = "Ex: Está pingando muito no guarda roupa, preciso de ajuda.",
                    value = reportViewModel.report.value.message,
                    onValueChange = { reportViewModel.changeMessage(it) },
                    isError = reportViewModel.inputContainsError("message"),
                    errorMessage = reportViewModel.getInputErrorMessage("message"),
                    singleLine = false
                )

                InputDropdownComp(
                    label = "Propriedade",
                    options = reportViewModel.propertiesOptions(),
                    selectedOption = reportViewModel.tenantPropertySelected.value.title,
                    onOptionSelected = { selectedOption ->
                        val property = reportViewModel.tenantProperties.value.first { it.id == selectedOption }
                        reportViewModel.changeProperty(property)
                    }
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    if (reportViewModel.messageError.value != "") {
                        MessageErrorComp(reportViewModel.messageError.value, 14.sp)
                    }

                    if (reportViewModel.onLoadingState.value) {
                        Box(Modifier.padding(20.dp)) {
                            CircularProgressIndicator()
                        }
                    } else {
                        ButtonComp(
                            "Enviar reportação",
                            { Icon(Icons.Default.Check, "enviar resposta") },
                            ConfirmColor,
                            { reportViewModel.createReportAction() },
                            155.dp,
                            16.sp
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun CreateReportScreenPreview() {
    val navControllerFake = rememberNavController()
    CreateReportScreen(ReportViewModel(navControllerFake))
}