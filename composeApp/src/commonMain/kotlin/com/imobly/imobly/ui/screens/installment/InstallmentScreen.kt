package com.imobly.imobly.ui.screens.installment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.enums.PaymentStatusEnum
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.colors.TitleColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.PaymentViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InstallmentScreen(paymentViewModel: PaymentViewModel) {

    val scrollState = rememberScrollState()

    paymentViewModel.whenStartingThePage()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = { SnackbarHost(paymentViewModel.snackMessage.value) },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TitleComp("Ver parcela", { paymentViewModel.goToShowPayments() }, true)

            Card(
                modifier = Modifier
                    .padding(20.dp)
                    .widthIn(max = 700.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                TextInfoComp("Mês", paymentViewModel.monthlyInstallment.value.month.label)
                TextInfoComp("Data de Vencimento", paymentViewModel.monthlyInstallment.value.dueDate)
                TextInfoComp("Valor", paymentViewModel.monthlyInstallment.value.monthlyRent)
                TextInfoComp("Status", remember{ paymentViewModel.monthlyInstallment.value.status.label })
            }
        }
    }
}

@Composable
fun TextInfoComp(label: String, value: String, color: Color = Color.Black) {
    Row(Modifier.padding(2.dp)) {
        Text(
            "${label}:  ",
            color = Color.Black,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontFamily = montserratFont(),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            value,
            color = color,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontFamily = montserratFont(),
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
fun InstallmentPreview() {
    val navFake = rememberNavController()
    InstallmentScreen(PaymentViewModel(navFake))
}