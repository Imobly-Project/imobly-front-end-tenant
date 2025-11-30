package com.imobly.imobly.ui.screens.showreports

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.PunchClock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.Report
import com.imobly.imobly.domain.enums.StatusReportEnum
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.CancelColor
import com.imobly.imobly.ui.theme.colors.ConfirmColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.ReportViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowReportsScreen(reportViewModel: ReportViewModel) {

    val reports: MutableState<List<Report>> = remember{ reportViewModel.reports}
    reportViewModel.findAllAction()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = {
            SnackbarHost(reportViewModel.snackMessage.value)
        },
        contentWindowInsets = WindowInsets.systemBars,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .background(BackGroundColor)
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TitleComp("Lista de Reportações", { reportViewModel.goToHome() }, true)

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        ButtonComp(
                            "Reportar problema",
                            {  Icon(Icons.Default.Add, "Sinal de soma") },
                            PrimaryColor,
                            { reportViewModel.goToCreateReport() }
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        SearchBarComp(
                            "Buscar reportações por título ou mensagem",
                            reportViewModel.searchText.value,
                            { reportViewModel.changeSearchText(it) },
                            { reportViewModel.searchAction() }

                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(reports.value) { report ->
                            ReportCardComp(report)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ReportCardComp(report: Report) {
    val statusColor:Color = when (report.status){
        StatusReportEnum.NEW -> Color(0xFF0059ff)
        StatusReportEnum.PENDING-> Color(0xFFFFC107)
        StatusReportEnum.RESOLVED-> ConfirmColor
        else -> CancelColor
    }

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
            Modifier.padding(16.dp)
        ) {

            Text(
                report.title,
                fontFamily = montserratFont(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            )

            Text(
                report.message,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                fontFamily = montserratFont(),
                fontSize = 14.sp,
                color = Color(0xFF555555)
            )

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccessTime, contentDescription = "Relógio", Modifier.padding(5.dp))
                    Text(
                        "${report.moment}",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontFamily = montserratFont()
                    )
                }
                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        report.status.label,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = montserratFont()
                    )
                }
            }

            if (report.response.isNotBlank()) {
                Box(Modifier.padding(5.dp)) {
                    Column(
                        Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Resposta:",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            fontFamily = montserratFont()
                        )

                        Text(
                            report.response,
                            fontSize = 14.sp,
                            color = Color(0xFF444444),
                            fontFamily = montserratFont()
                        )
                    }
                }
            }

            Row {
                Text(
                    "Locatário: ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(top = 8.dp),
                    fontFamily = montserratFont()
                )

                Text(
                    "${report.tenant.firstName}  ${report.tenant.lastName}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(top = 8.dp),
                    fontFamily = montserratFont()
                )
            }

            Row {
                Text(
                    "Imóvel: ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(top = 8.dp),
                    fontFamily = montserratFont()
                )

                Text(
                    report.property.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(top = 8.dp),
                    fontFamily = montserratFont()
                )
            }
        }
    }
}

@Composable
@Preview
fun ShowReportsScreenPreview() {
    val navControllerFake = rememberNavController()
    ShowReportsScreen(ReportViewModel(navControllerFake))
}