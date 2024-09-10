package com.nolawiworkineh.core.data.di

import com.nolawiworkineh.core.data.auth.EncryptedSessionStorage
import com.nolawiworkineh.core.data.networking.HttpClientFactory
import com.nolawiworkineh.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

// **Define the DI module for the auth data layer**:
// sets up a single instance of the HttpClientFactory
var coreDataModule = module {
    // Provides a single instance of the HTTP client by using the HttpClientFactory to build it
    single {
        HttpClientFactory().build()
    }

    // Provides a single instance of the SessionStorage by using the EncryptedSessionStorage
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}