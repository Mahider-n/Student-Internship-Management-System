package com.site7x24learn.internshipfrontend.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LandingViewModel : ViewModel() {
    // Single event flow for navigation
    private val _navigationEvents = MutableSharedFlow<String>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun navigateWithLoading(destination: String) {
        viewModelScope.launch {
            _navigationEvents.emit("waiting")
            delay(450)

            // Handle any business logic first
            // Then emit navigation event
            _navigationEvents.emit(destination)
        }
    }
}
