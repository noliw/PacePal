package com.nolawiworkineh.core.data.di

import com.nolawiworkineh.core.data.networking.HttpClientFactory
import org.koin.dsl.module

// **Define the DI module for the auth data layer**:
// sets up a single instance of the HttpClientFactory
var coreDataModule = module {
    // Provides a single instance of the HTTP client by using the HttpClientFactory to build it
    single {
        HttpClientFactory().build()
    }
}