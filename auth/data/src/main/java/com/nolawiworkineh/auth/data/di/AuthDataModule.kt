package com.nolawiworkineh.auth.data.di

import com.nolawiworkineh.auth.data.AuthRepositoryImpl
import com.nolawiworkineh.auth.data.EmailPatternValidator
import com.nolawiworkineh.auth.domain.AuthRepository
import com.nolawiworkineh.auth.domain.PatternValidator
import com.nolawiworkineh.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


// **Define the DI module for the auth data layer**:
// This module contains all the DI setup for the auth feature.
val authDataModule = module {

    // **Provide a singleton instance of PatternValidator**:
    // This interface ensures that the email validator follows the contract for validating patterns.
    single<PatternValidator> {
        // **Use the EmailPatternValidator implementation**:
        // This specific validator checks whether email formats are valid.
        EmailPatternValidator
    }

    // **Provide a singleton instance of UserDataValidator**:
    // This validator checks the strength and format of passwords. Koin automatically creates the instance.
    singleOf(::UserDataValidator)

    /*
    - AuthRepositoryImpl is the implementation of the AuthRepository interface.
    - singleOf creates a singleton of AuthRepositoryImpl (meaning only one instance of it will exist throughout the app).
    - .bind<AuthRepository>() tells Koin that whenever the app asks for an AuthRepository, it should provide the AuthRepositoryImpl instance.
     */
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}
