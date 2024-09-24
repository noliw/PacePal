package com.nolawiworkineh.run.presentation.di

import com.nolawiworkineh.run.domain.RunningTracker
import com.nolawiworkineh.run.presentation.active_run.ActiveRunViewModel
import com.nolawiworkineh.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val runViewModelModule = module {
    singleOf(::RunningTracker)
    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)

}