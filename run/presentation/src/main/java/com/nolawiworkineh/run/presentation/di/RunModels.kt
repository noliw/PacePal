package com.nolawiworkineh.run.presentation.di

import com.nolawiworkineh.run.presentation.active_run.ActiveRunViewModel
import com.nolawiworkineh.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val runViewModelModule = module {
    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)

}