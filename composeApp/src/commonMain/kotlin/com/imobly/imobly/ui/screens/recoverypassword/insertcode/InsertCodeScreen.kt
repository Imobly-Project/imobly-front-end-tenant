package com.imobly.imobly.ui.screens.recoverypassword.insertcode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.input.InputOtpComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.ResetPasswordViewModel
import imobly_front_end_tenant.composeapp.generated.resources.Res
import imobly_front_end_tenant.composeapp.generated.resources.image_logo_white
import org.jetbrains.compose.resources.painterResource

@Composable
fun InsertCodeScreen(resetPasswordViewModel: ResetPasswordViewModel) {
    val scrollState = rememberScrollState()

    MaterialTheme {
        Scaffold(
            topBar = {},
            snackbarHost = {
                SnackbarHost(resetPasswordViewModel.snackMessage.value)
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
                    TitleComp("Redefinir senha", { resetPasswordViewModel.goToLogin() }, true, 40.sp)
                    Spacer(Modifier.size(40.dp))
                    Column(
                        Modifier
                            .widthIn(max = 1000.dp)
                            .fillMaxWidth(0.9f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Insira o código que enviamos ao seu e-mail para prosseguir com a troca da senha", fontFamily = montserratFont())
                        InputOtpComp(
                            value = resetPasswordViewModel.code.value,
                            onValueChange ={ resetPasswordViewModel.changeCode(it) },
                            isError = resetPasswordViewModel.inputContainsError("code"),
                        )
                    }

                    if (resetPasswordViewModel.messageError.value != "") {
                        MessageErrorComp(resetPasswordViewModel.messageError.value, 16.sp)
                    }
                    ButtonComp(
                        text="Continuar",
                        color=PrimaryColor,
                        action = { resetPasswordViewModel.validateCodeAction() },
                        icon = {}
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.fillMaxSize().padding(vertical = 50.dp),
                    ) {

                        Text(
                            "Voltar ao Login",
                            textAlign = TextAlign.Center,
                            fontFamily = montserratFont(),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { resetPasswordViewModel.goToLogin() }
                        )

                    }
                }
            }
        }
    }
}