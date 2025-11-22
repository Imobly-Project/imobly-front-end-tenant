package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.httpclient.CategoryHttpClient
import com.imobly.imobly.api.httpclient.PropertyHttpClient
import com.imobly.imobly.domain.Property
import com.imobly.imobly.domain.Category
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class PropertyViewModel(private val navController: NavHostController): ViewModel() {
    val categories: MutableState<List<Category>> = mutableStateOf(emptyList())
    val searchText: MutableState<String> = mutableStateOf("")
    val properties: MutableState<List<Property>> = mutableStateOf(emptyList())

    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

    fun changeSearchText(it: String) {
        searchText.value = it
    }

    fun findAllAction() {
        viewModelScope.launch {
            val httpClient = PropertyHttpClient(createHttpClient())
            val categoryHttpClient = CategoryHttpClient(createHttpClient())
            categories.value = categoryHttpClient.searchAllByTitle()
            properties.value = httpClient.searchAllByTitle()
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
        }
    }

    fun categoriesOptions(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        categories.value.forEach {
            map[it.title] = it.id ?: ""
        }
        return map
    }
}