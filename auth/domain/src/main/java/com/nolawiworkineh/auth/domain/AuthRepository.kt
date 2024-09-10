package com.nolawiworkineh.auth.domain

import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.EmptyDataResult


// This interface defines what actions the app should be able to perform related to user authentication.
interface AuthRepository {

    // This function will register a user.
    // It takes an email and password and tries to register the user.
    // It's a 'suspend' function, which means it works asynchronously (in the background)
    // because registering a user requires communicating with a remote server.
    // The function returns an EmptyDataResult, which tells us if the registration succeeded or failed due to a network issue.
    suspend fun register(email: String, password: String): EmptyDataResult<DataError.Network>

    //  This function sets up a instruction to login a user.
    suspend fun login(email: String, password: String): EmptyDataResult<DataError.Network>
}

