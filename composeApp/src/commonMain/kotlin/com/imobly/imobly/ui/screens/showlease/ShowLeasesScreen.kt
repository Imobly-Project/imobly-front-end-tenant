package com.imobly.imobly.ui.screens.showlease

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Money
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
import com.imobly.imobly.domain.Lease
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.LeaseViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowLeasesScreen(leaseViewModel: LeaseViewModel) {
    leaseViewModel.findAllAction()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = {
            SnackbarHost(leaseViewModel.snackMessage.value)
        },
        contentWindowInsets = WindowInsets.systemBars,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(BackGroundColor)
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            TitleComp(text = "Locações", { leaseViewModel.goToHome() }, true)

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SearchBarComp(
                    "Buscar locações pelo título do imóvel",
                    leaseViewModel.searchText.value,
                    { leaseViewModel.changeSearchText(it) },
                    { leaseViewModel.searchAction() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                items(leaseViewModel.leases.value) { lease ->
                    LeaseCard(
                        lease = lease,
                        leaseViewModel = leaseViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun LeaseCard(lease: Lease, leaseViewModel: LeaseViewModel) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .widthIn(max = 700.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextInfoComp("Imóvel", lease.property.title)
            TextInfoComp("Locatário", lease.tenant.firstName)
            TextInfoComp("Data de início", lease.startDate)
            TextInfoComp("Data de término", lease.endDate)
            TextInfoComp("Dia do vencimento", lease.paymentDueDay)
            TextInfoComp("Aluguel", lease.monthlyRent)
            TextInfoComp("Deposíto", lease.securityDeposit)
            TextInfoComp("Criado em", lease.createdAt)
            TextInfoComp("Última vez atualizado em", lease.lastUpdatedAt)
            TextInfoComp("Duração em meses", lease.durationInMonths)
            TextInfoComp(
                "Status",
                if (lease.isEnabled) "Ativo" else "Inativo",
                if (lease.isEnabled) ConfirmColor else CancelColor
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                ButtonComp(
                    "Ver pagamentos",
                    { Icon(Icons.Default.Money, "Dinheiro") },
                    PrimaryColor,
                    { leaseViewModel.goToShowPayments(lease) }
                )
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
fun ShowLeasesPreview() {
    val navControllerFake = rememberNavController()
    ShowLeasesScreen(LeaseViewModel(navControllerFake))
}
