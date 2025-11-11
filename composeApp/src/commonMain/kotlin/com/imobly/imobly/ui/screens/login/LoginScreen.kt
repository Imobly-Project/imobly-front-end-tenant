package com.imobly.imobly.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.input.InputPasswordComp
import com.imobly.imobly.ui.components.messageerror.MessageErrorComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.LoginViewModel
import imobly_front_end_tenant.composeapp.generated.resources.Res
import imobly_front_end_tenant.composeapp.generated.resources.image_logo_white
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    val scrollState = rememberScrollState()

    MaterialTheme {
        Scaffold(
            topBar = {},
            snackbarHost = {
                SnackbarHost(loginViewModel.snackMessage.value)
            },
            contentWindowInsets = WindowInsets.systemBars
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .background(Color(242, 96, 63))
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
                            Color.White,
                            shape = RoundedCornerShape(50.dp, 50.dp)
                        )
                        .clip(RoundedCornerShape(50.dp, 50.dp)),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(Modifier.size(60.dp))
                    TitleComp("Iniciar sessão", { loginViewModel.goToHome() }, true, 40.sp)
                    Spacer(Modifier.size(40.dp))
                    Column(
                        Modifier
                            .widthIn(max = 1000.dp)
                            .fillMaxWidth(0.9f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InputComp(
                            label = "E-mail",
                            placeholder = "Ex: joao@dominio.com",
                            value = loginViewModel.email.value,
                            onValueChange = { loginViewModel.changeEmail(it) },
                            isError = loginViewModel.inputContainsError("email"),
                            errorMessage = loginViewModel.getInputErrorMessage("email")
                        )

                        InputPasswordComp(
                            label = "Senha",
                            placeholder = "Ex: ABCD1234",
                            value = loginViewModel.password.value,
                            onValueChange = { loginViewModel.changePassword(it) },
                            isError = loginViewModel.inputContainsError("password"),
                            errorMessage = loginViewModel.getInputErrorMessage("password"),
                            passwordVisible = loginViewModel.passwordVisibilityState.value,
                            changePasswordVisible = { loginViewModel.changePasswordVisibility() }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .widthIn(max = 800.dp)
                            .fillMaxWidth(0.8f),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            "Esqueceu a senha?",
                            fontFamily = montserratFont(),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable {  }
                        )
                    }

                    if (loginViewModel.messageError.value != "") {
                        MessageErrorComp(loginViewModel.messageError.value, 16.sp)
                    }
                    ButtonComp(
                        "Entrar",
                        { Icon(Icons.AutoMirrored.Filled.Login, "login") },
                        PrimaryColor,
                        { loginViewModel.signInAction() }
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.fillMaxSize().padding(vertical = 50.dp),
                    ) {

                        Text(
                            "Não possui conta? ",
                            textAlign = TextAlign.Center,
                            fontFamily = montserratFont(),
                            fontWeight = FontWeight.Medium,
                        )

                        Text(
                            "Cadastre-se",
                            textAlign = TextAlign.Center,
                            fontFamily = montserratFont(),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { loginViewModel.goToSignUp() }
                        )

                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    val navControllerFake = rememberNavController()
    LoginScreen(LoginViewModel(navControllerFake))
}