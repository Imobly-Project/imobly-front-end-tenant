package com.imobly.imobly.ui.screens.showproperties

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Garage
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.imobly.imobly.domain.Property
import com.imobly.imobly.ui.components.button.ButtonComp
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.BackGroundColor
import com.imobly.imobly.ui.theme.colors.PrimaryColor
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.PropertyViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowPropertiesScreen(propertyViewModel: PropertyViewModel) {

    val properties: MutableState<List<Property>> = remember { propertyViewModel.properties }
    propertyViewModel.findAllAction()

    Scaffold(
        topBar = { TopBarComp() },
        snackbarHost = {
            SnackbarHost(propertyViewModel.snackMessage.value)
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                    }


                    TitleComp("Imóveis", {
                        propertyViewModel.goToHome()
                    }, true)

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        SearchBarComp(
                            "Buscar um imóvel",
                            propertyViewModel.searchText.value,
                            { propertyViewModel.changeSearchText(it) },
                            { propertyViewModel.searchAction() }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(properties.value) { property ->
                            PropertyCardComp(property, propertyViewModel)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PropertyCardComp(property: Property, propertyViewModel: PropertyViewModel) {
    Card(
        modifier = Modifier
            .widthIn(max = 1000.dp)
            .fillMaxWidth()
            .padding(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentAlignment = Alignment.Center
            ) {

                KamelImage(
                    resource = { asyncPainterResource(property.pathImages.first()) },
                    contentDescription = "Imagem do imóvel ${property.title}",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop,
                    onLoading = { progress -> CircularProgressIndicator({ progress }) },
                    onFailure = { CircularProgressIndicator() }
                )


                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(
                            color = Color(0xFFF2603F),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "R$ ${property.monthlyRent}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White,
                        fontFamily = montserratFont()
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = property.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF2D5B7A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = montserratFont()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${property.address.neighborhood}, ${property.address.street} Nº ${property.address.number}, ${property.address.city}/${property.address.state}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = montserratFont()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextInfoComp(
                            "Área: ${property.area} m²",
                            { Icon(Icons.Default.SquareFoot, "Área") }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        TextInfoComp(
                            "Nª quartos: ${property.bedrooms}",
                            { Icon(Icons.Default.Bed, "Quartos") }
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextInfoComp(
                            "Vagas garagem: ${property.garageSpaces}",
                            { Icon(Icons.Default.Garage, "Garagem") }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        TextInfoComp(
                            "Nª banheiros: ${property.bathrooms}",
                            { Icon(Icons.Default.Bathtub, "Banheiros") }
                        )
                    }
                }

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    ButtonComp(
                        "Agendar visita",
                        { Icon(Icons.Default.CalendarToday, "Agendamento") },
                        PrimaryColor,
                        { propertyViewModel.goToCreateAppointment(property) }
                    )
                }
            }
        }
    }
}

@Composable
fun TextInfoComp(text: String, icon: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(
                color = Color(0xFFF2603F).copy(alpha = 0.2f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }

    Spacer(modifier = Modifier.width(12.dp))

    Text(
        text = text,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Color(0xFF333333),
        fontFamily = montserratFont()
    )
}

@Preview
@Composable
fun ShowPropertiesPreview() {
    val navControllerFake = rememberNavController()
    ShowPropertiesScreen(PropertyViewModel(navControllerFake))
}