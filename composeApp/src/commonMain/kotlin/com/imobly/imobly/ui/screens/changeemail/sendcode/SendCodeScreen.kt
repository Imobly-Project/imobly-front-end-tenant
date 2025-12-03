package com.imobly.imobly.ui.screens.changeemail.sendcode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.input.InputOtpComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.ChangeEmailViewModel
import imobly_front_end_tenant.composeapp.generated.resources.Res
import imobly_front_end_tenant.composeapp.generated.resources.image_logo_white
import org.jetbrains.compose.resources.painterResource

@Composable
fun SendCodeScreen(changeEmailViewModel: ChangeEmailViewModel) {
    val scrollState = rememberScrollState()

    changeEmailViewModel.resetPage()

    MaterialTheme {
        Scaffold(
            topBar = {},
            snackbarHost = {
                SnackbarHost(changeEmailViewModel.snackMessage.value)
            },
            contentWindowInsets = WindowInsets.systemBars
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .background(PrimaryColor)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                            BackGroundColor,
                            shape = RoundedCornerShape(50.dp, 50.dp)
                        )
                        .clip(RoundedCornerShape(50.dp, 50.dp)),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(Modifier.size(60.dp))
                    TitleComp("Redefinir e-mail", { changeEmailViewModel.goToSendEmail() }, true, 40.sp)
                    Spacer(Modifier.size(40.dp))
                    Column(
                        Modifier
                            .widthIn(max = 1000.dp)
                            .fillMaxWidth(0.9f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Insira o código que enviamos no seu novo e-mail para prosseguir com a troca", fontFamily = montserratFont())
                        InputOtpComp(
                            value = changeEmailViewModel.code.value,
                            onValueChange ={ changeEmailViewModel.changeCode(it) },
                            isError = changeEmailViewModel.inputContainsError("code"),
                        )
                    }


                    if (changeEmailViewModel.messageError.value != "") {
                        MessageErrorComp(changeEmailViewModel.messageError.value, 16.sp)
                    }

                    ButtonComp(
                        text = "Redefinir e-mail",
                        color = PrimaryColor,
                        action = { changeEmailViewModel.sendCodeAction() },
                        icon = {}
                    )
                }
            }
        }
    }
}