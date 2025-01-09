package com.nolawiworkineh.run.network.di

import com.nolawiworkineh.core.domain.run.RemoteRunDataSource
import com.nolawiworkineh.run.network.KtorRemoteRunDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorRemoteRunDataSource).bind<RemoteRunDataSource>()
}