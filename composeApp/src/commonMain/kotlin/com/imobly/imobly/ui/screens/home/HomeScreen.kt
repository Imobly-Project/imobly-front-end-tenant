package com.imobly.imobly.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.OtherHouses
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.imobly.imobly.ui.components.cards.CardButtonComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.colors.TitleColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val horizantalScrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopBarComp()
        },
//        snackbarHost = { SnackbarHost( tenantViewModel.snackMessage.value ) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .background(BackGroundColor)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

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
                        .height(400.dp)
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
                                text = "Login",
                                icon = {
                                    Icon(
                                        Icons.Outlined.Category,
                                        contentDescription = "Login",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { navController.navigate("login") },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }
                        item {
                            CardButtonComp(
                                text = "SignUp",
                                icon = {
                                    Icon(
                                        Icons.Outlined.Category,
                                        contentDescription = "SignUp",
                                        modifier = Modifier.fillMaxSize().padding(20.dp),
                                        tint = backgroundColor,
                                    )
                                },
                                action = { navController.navigate("signup") },
                                backgroundColor = backgroundColor,
                                highlightColor = highlightColor

                            )
                        }

                    }
                }

            }
        })

}
@Composable
@Preview
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}