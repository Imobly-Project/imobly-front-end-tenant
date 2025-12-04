package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.httpclient.CategoryHttpClient
import com.imobly.imobly.api.httpclient.PropertyHttpClient
import com.imobly.imobly.domain.Property
import com.imobly.imobly.domain.Category
import imobly_front_end_tenant.composeapp.generated.resources.Res
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class PropertyViewModel(private val navController: NavHostController): ViewModel() {
    val categories: MutableState<List<Category>> = mutableStateOf(emptyList())
    val searchText: MutableState<String> = mutableStateOf("")
    val properties: MutableState<List<Property>> = mutableStateOf(emptyList())

    val filteredProperties : MutableState<List<Property>> = mutableStateOf(emptyList())

    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

    val filterAreaStart = mutableStateOf(0f)
    val filterAreaEnd = mutableStateOf(400f)
    val filterQuartos = mutableStateOf(0)
    val filterGaragem = mutableStateOf(0)
    val filterBanheiros = mutableStateOf(0)
    val filterEndereco = mutableStateOf("")

    val categoriaTodos = Category(
        id = "",
        title = "Todos"
    )

    var filterCategoria = mutableStateOf(categoriaTodos)


    val filterValueOptions = mutableStateListOf(1,2,3,4,5,6)

    fun applyFilter(){
        val list = properties.value.filter { property ->

            val area = property.area.toFloatOrNull() ?: 0f
            val bedrooms = property.bedrooms.toIntOrNull() ?: 0
            val bathrooms = property.bathrooms.toIntOrNull() ?: 0
            val garage = property.garageSpaces.toIntOrNull() ?: 0

            val areaOk = area in filterAreaStart.value..filterAreaEnd.value
            val quartosOk = if (filterQuartos.value == 0) true else bedrooms == filterQuartos.value
            val garagemOk = if (filterGaragem.value == 0) true else garage == filterGaragem.value
            val banheirosOk = if (filterBanheiros.value == 0) true else bathrooms == filterBanheiros.value
            val enderecoOk = filterEndereco.value.isBlank() ||
                    property.address.city.contains(filterEndereco.value, ignoreCase = true) ||
                    property.address.state.contains(filterEndereco.value, ignoreCase = true) ||
                    property.address.street.contains(filterEndereco.value, ignoreCase = true) ||
                    property.address.neighborhood.contains(filterEndereco.value, ignoreCase = true)
            val categoriaOk =
                filterCategoria.value.id!!.isBlank() || property.category.id == filterCategoria.value.id

            areaOk && quartosOk && garagemOk && banheirosOk && enderecoOk && categoriaOk
        }

        filteredProperties.value = list
    }
    fun goToCreateAppointment(property: Property) {
        SharedRepository.property = property
        navController.navigate("createappointment")
    }

    fun changeSearchText(it: String) {
        searchText.value = it
    }

    fun findAllAction() {
        viewModelScope.launch {
            val httpClient = PropertyHttpClient(createHttpClient())
            val categoryHttpClient = CategoryHttpClient(createHttpClient())
            categories.value = categoryHttpClient.searchAllByTitle()
            properties.value = httpClient.searchAllByTitle()
            filteredProperties.value = properties.value
        }
    }


    fun goToHome() {
        navController.navigate("home")
    }

    fun searchAction() {
        viewModelScope.launch {
            val httpClient = PropertyHttpClient(createHttpClient())
            val list = httpClient.searchAllByTitle(searchText.value)
            properties.value = list
            applyFilter()
        }
    }

    fun categoriasParaFiltro(): List<Category> {
        return listOf(categoriaTodos) + categories.value
    }
    fun categoriesOptions(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        categoriasParaFiltro().forEach {
            map[it.title] = it.id ?: ""
        }
        return map
    }

    fun changeCategory(category: Category) {
        filterCategoria.value = category
        applyFilter()
    }

    fun clearCategoryFilter() {
        filterCategoria.value = categoriaTodos
        applyFilter()
    }

}