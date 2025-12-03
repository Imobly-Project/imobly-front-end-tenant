package com.imobly.imobly.ui.screens.showproperties

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
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
import com.imobly.imobly.ui.components.input.InputComp
import com.imobly.imobly.ui.components.searchbar.SearchBarComp
import com.imobly.imobly.ui.components.title.TitleComp
import com.imobly.imobly.ui.components.topbar.TopBarComp
import com.imobly.imobly.ui.theme.colors.*
import com.imobly.imobly.ui.theme.fonts.montserratFont
import com.imobly.imobly.viewmodel.PropertyViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShowPropertiesScreen(propertyViewModel: PropertyViewModel) {

    val properties: MutableState<List<Property>> = remember { propertyViewModel.properties }
    LaunchedEffect(Unit) {
        propertyViewModel.findAllAction()
    }

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


                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center){
                        FilterModal(propertyViewModel)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(propertyViewModel.filteredProperties.value) { property ->
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

@Composable
fun CustomRangeSlider(onRangeChanged: (Float, Float) -> Unit) {
    var sliderPosition by remember { mutableStateOf(0f..400f) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Filtrar por área", fontFamily = montserratFont())

        RangeSlider(
            value = sliderPosition,
            steps = 7,
            onValueChange = { range -> sliderPosition = range },
            valueRange = 0f..400f,
            onValueChangeFinished = {
                onRangeChanged(sliderPosition.start, sliderPosition.endInclusive)

            },
            colors = SliderDefaults.colors(
                activeTickColor = PrimaryColor,
                activeTrackColor = PrimaryColor,
                inactiveTrackColor = DisabledColor,
                thumbColor = PrimaryColor,
                inactiveTickColor = TitleColor,
                disabledActiveTickColor = TitleColor,
                disabledInactiveTickColor = TitleColor
            )
        )
        Text(text = "De ${sliderPosition.start} m² até ${sliderPosition.endInclusive} m²", fontFamily = montserratFont())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipComp(
    title : String,
    options:List<Int>,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            fontSize = 16.sp,
            fontFamily = montserratFont(),
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
        ) {
            options.forEach { filter ->

                val isSelected = selectedOption == filter

                FilterChip(
                    selected = isSelected,
                    onClick = {
                        if (isSelected) {
                            onOptionSelected(0)
                        } else {
                            onOptionSelected(filter)
                        }
                              },
                    label = {
                        Text(
                            text = filter.toString(),
                            fontFamily = montserratFont(),
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    leadingIcon = {
//                        if (isSelected)
//                            Icon(
//                                Icons.Default.Check,
//                                contentDescription = null,
//                                tint = Color.White
//                            )
                    },
                    modifier = Modifier.height(40.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryColor,
                        selectedLabelColor = Color.White,
                        containerColor = BackGroundColor,
                        labelColor = TitleColor
                    ),
                    border = if (isSelected) null else
                        FilterChipDefaults.filterChipBorder(
                            borderColor = Color(0xFFBBBBBB),
                            enabled = true,
                            selected = isSelected
                        )
                )
            }
        }
    }
}

@Composable
fun AddressFilterField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Filtrar por endereço:",
            fontFamily = montserratFont(),
            fontSize = 16.sp,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        InputComp(
            label = "Endereço:",
            placeholder = "Digite bairro, rua, cidade...",
            value = value,
            onValueChange = onValueChange,
            singleLine = true
        )
    }
}

@Composable
fun PropertyFiltersComp(propertyViewModel: PropertyViewModel){
    Box(Modifier.width(500.dp)) {

        Column(Modifier.fillMaxWidth()){

            CustomRangeSlider(
                onRangeChanged = { start, end ->
                    propertyViewModel.filterAreaStart.value = start
                    propertyViewModel.filterAreaEnd.value = end
                    propertyViewModel.applyFilter()
                })

            Spacer(Modifier.height(16.dp))

            ChipComp(
                title = "Nº de quartos",
                options = propertyViewModel.filterValueOptions,
                selectedOption = propertyViewModel.filterQuartos.value,
                onOptionSelected = { newValue->
                    propertyViewModel.filterQuartos.value = newValue
                    propertyViewModel.applyFilter()
                }
            )

            Spacer(Modifier.height(16.dp))

            ChipComp(
                title = "Nº de vagas na garagem",
                options = propertyViewModel.filterValueOptions,
                selectedOption = propertyViewModel.filterGaragem.value,
                onOptionSelected = { newValue->
                    propertyViewModel.filterGaragem.value = newValue
                    propertyViewModel.applyFilter()
                }
            )

            Spacer(Modifier.height(16.dp))

            ChipComp(
                title = "Nº de banheiros",
                options = propertyViewModel.filterValueOptions,
                selectedOption = propertyViewModel.filterBanheiros.value,
                onOptionSelected = { newValue->
                    propertyViewModel.filterBanheiros.value = newValue
                    propertyViewModel.applyFilter()
                }
            )

            Spacer(Modifier.height(16.dp))

            AddressFilterField(
                value = propertyViewModel.filterEndereco.value,
                onValueChange = {
                    propertyViewModel.filterEndereco.value = it
                    propertyViewModel.applyFilter()
                }
            )

            HorizontalDivider()

        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModal(propertyViewModel: PropertyViewModel){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    ButtonComp(
        text = "Filtrar",
        action = { showBottomSheet = true },
        icon = {},
        color = PrimaryColor
    )
    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = BackGroundColor,
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).imePadding(), horizontalAlignment = Alignment.CenterHorizontally) {

                PropertyFiltersComp(propertyViewModel)

                ButtonComp(
                    action = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    },
                    text = "Fechar",
                    icon = { Icon(Icons.Default.Cancel, "fechar") },
                    color = CancelColor
                )
            }
        }
    }
}
@Preview
@Composable
fun ShowPropertiesPreview() {
    val navControllerFake = rememberNavController()
    ShowPropertiesScreen(PropertyViewModel(navControllerFake))
}