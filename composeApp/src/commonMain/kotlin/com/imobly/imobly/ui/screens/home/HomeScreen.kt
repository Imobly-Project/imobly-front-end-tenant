package com.imobly.imobly.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.OtherHouses
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.colors.TitleColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.HomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val scrollState = rememberScrollState()

    MaterialTheme {
        Scaffold(
            topBar = { TopBarComp() },
            snackbarHost = {
                SnackbarHost(homeViewModel.snackMessage.value)
            },
            contentWindowInsets = WindowInsets.systemBars,
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .background(BackGroundColor)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                val adaptiveInfo = currentWindowAdaptiveInfo()
                val adaptiveWidth = when {
                    adaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND) -> 1100.dp
                    adaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> 700.dp
                    else -> 600.dp
                }

                TitleComp("Pagina inicial", fontSize = 32.sp, backButton = false, buttonBackAction = {})

                Spacer(Modifier.height(30.dp))


                val highlightColor = PrimaryColor
                val backgroundColor = BackGroundColor

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    Alignment.Center

                ) {
                    LazyVerticalGrid(
                        columns = when (adaptiveWidth.value.toInt()) {
                            1100 -> GridCells.Fixed(3)
                            else -> GridCells.Fixed(2)
                        },
                        modifier = Modifier
                            .width(adaptiveWidth),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        userScrollEnabled = false
                    ) {
                        item {
                            CardButtonComp(
                                text = "Meu perfil",
                                icon = {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = "Perfil",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToMyProfile() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }

                        item {
                            CardButtonComp(
                                text = "Propriedades",
                                icon = {
                                    Icon(
                                        Icons.Outlined.OtherHouses,
                                        contentDescription = "Casas",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToShowProperties() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }

                        item {
                            CardButtonComp(
                                text = "Reportações",
                                icon = {
                                    Icon(
                                        Icons.Outlined.Feedback,
                                        contentDescription = "Relatos",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToShowReports() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }

                        item {
                            CardButtonComp(
                                text = "Contratos",
                                icon = {
                                    Icon(
                                        Icons.Outlined.ManageAccounts,
                                        contentDescription = "Contratos",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { homeViewModel.goToShowLeases() },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }
                        if (homeViewModel.isLogged()) {

                            item {
                                CardButtonComp(
                                    text = "Trocar o E-mail",
                                    icon = {
                                        Icon(
                                            Icons.Default.Email,
                                            contentDescription = "Email",
                                            modifier = Modifier.fillMaxSize().padding(20.dp),
                                            tint = backgroundColor,
                                        )
                                    },
                                    action = { homeViewModel.goToSendEmail() },
                                    backgroundColor = backgroundColor,
                                    highlightColor = highlightColor

                                )
                            }

                            item {
                                CardButtonComp(
                                    text = "Deslogar",
                                    icon = {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ExitToApp,
                                            contentDescription = "Perfil",
                                            modifier = Modifier.fillMaxSize().padding(20.dp),
                                            tint = backgroundColor,
                                        )
                                    },
                                    action = { homeViewModel.logOut() },
                                    backgroundColor = backgroundColor,
                                    highlightColor = highlightColor
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
fun CardButtonComp(
    text: String,
    width: Dp = 200.dp,
    icon: @Composable () -> Unit,
    backgroundColor: Color = BackGroundColor,
    highlightColor: Color = PrimaryColor,
    action: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .width(width)
            .height(100.dp),
        onClick = action,

        ) {
        Row {
            Column(
                Modifier.background(highlightColor)
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                icon()
            }

            Column(
                Modifier.background(backgroundColor)
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    fontFamily = montserratFont(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = TitleColor
                )
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(HomeViewModel(navController))
}