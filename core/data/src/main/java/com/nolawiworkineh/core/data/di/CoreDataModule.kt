package com.nolawiworkineh.core.data.di

import com.nolawiworkineh.core.data.networking.HttpClientFactory
import org.koin.dsl.module

var coreDataModule = module {
single{
    HttpClientFactory().build()
}
}