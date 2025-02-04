package com.nolawiworkineh.pacepal

import android.app.Application
import com.nolawiworkineh.auth.data.di.authDataModule
import com.nolawiworkineh.auth.presentation.di.authViewModelModule
import com.nolawiworkineh.core.data.di.coreDataModule
import com.nolawiworkineh.core.database.di.databaseModule
import com.nolawiworkineh.pacepal.di.appModule
import com.nolawiworkineh.run.location.di.locationModule
import com.nolawiworkineh.run.network.di.networkModule
import com.nolawiworkineh.run.presentation.di.runViewModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class PacePalApp : Application() {

    // Defining an application-wide CoroutineScope with SupervisorJob
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        // **Initialize Timber for logging in debug mode**:
        // Timber helps with logging debug messages.
        if (BuildConfig.DEBUG) {
            // **DebugTree**: A Timber tree for logging debug messages.
            Timber.plant(Timber.DebugTree())
        }

        // **Start Koin for dependency injection**: This initializes Koin with the app’s modules.
        startKoin {
            androidLogger() // Logs Koin activity, helpful for debugging DI
            androidContext(this@PacePalApp) // Provides the application context for Koin
            modules(
                authDataModule, // Injects dependencies for the auth data layer
                authViewModelModule, // Injects dependencies for the auth ViewModels
                appModule, // Provides global dependencies for the app
                coreDataModule, // Injects dependencies for the core data layer
                runViewModelModule,
                locationModule,
                databaseModule,
                networkModule,
            )
        }
    }
}
