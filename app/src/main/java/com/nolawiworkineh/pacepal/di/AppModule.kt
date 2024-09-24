package com.nolawiworkineh.pacepal.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.nolawiworkineh.pacepal.MainViewModel
import com.nolawiworkineh.pacepal.PacePalApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

// **appModule**: Koin module that provides dependencies used across the app.
val appModule = module {

    // **EncryptedSharedPreferences**: Provides an encrypted instance of SharedPreferences for securely storing sensitive data.
    single<SharedPreferences> {

        EncryptedSharedPreferences.create(
            androidApplication(),  // Context (required to create SharedPreferences)
            "auth_preferences",  // Name of the SharedPreferences file
            MasterKey.Builder(androidApplication())  // MasterKey for encryption
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)  // Encryption scheme
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,  // Encryption for keys
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM  // Encryption for values
        )
    }

    // Providing the application-wide CoroutineScope
    single<CoroutineScope>{
        (androidApplication() as PacePalApp).applicationScope
    }

    viewModelOf(::MainViewModel)
}
