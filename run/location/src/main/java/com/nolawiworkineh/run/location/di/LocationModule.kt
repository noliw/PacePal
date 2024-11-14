package com.nolawiworkineh.run.location.di

import com.nolawiworkineh.run.domain.LocationObserver
import com.nolawiworkineh.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}