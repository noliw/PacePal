package com.nolawiworkineh.auth.presentation.di

import com.nolawiworkineh.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

// **Define the DI module for the auth view model layer**:
// This module contains all the DI setup for the auth feature.
val authViewModelModule = module {
    // **Provide a singleton instance of RegisterViewModel**:
    // Koin will create and manage the lifecycle of this ViewModel.
    viewModelOf(::RegisterViewModel)
}
