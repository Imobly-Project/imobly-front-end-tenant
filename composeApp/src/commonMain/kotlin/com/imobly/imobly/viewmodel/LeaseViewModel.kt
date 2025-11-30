package com.imobly.imobly.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imobly.imobly.api.createHttpClient
import com.imobly.imobly.api.httpclient.LeaseHttpClient
import com.imobly.imobly.domain.Lease
import kotlinx.coroutines.launch

class LeaseViewModel(private val navController: NavHostController): ViewModel() {
    val leases = mutableStateOf<List<Lease>>(emptyList())

    val searchText: MutableState<String> = mutableStateOf("")

    val snackMessage : MutableState<SnackbarHostState> = mutableStateOf( SnackbarHostState() )

    fun changeSearchText(it: String) {
        searchText.value = it
    }

    fun findAllAction() {
        viewModelScope.launch {
            val leaseHttpClient = LeaseHttpClient(createHttpClient())

            val leasesFound = leaseHttpClient.searchAllByTitleOrName()
            leases.value = leasesFound
        }
    }

    fun searchAction() {
        viewModelScope.launch {
            val httpClient = LeaseHttpClient(createHttpClient())
            val list = httpClient.searchAllByTitleOrName(searchText.value)
            leases.value = list
        }
    }

    fun goToHome() {
        navController.navigate("home")
    }

    fun goToShowPayments() {

    }
}