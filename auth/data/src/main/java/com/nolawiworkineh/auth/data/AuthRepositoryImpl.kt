package com.nolawiworkineh.auth.data

import com.nolawiworkineh.auth.domain.AuthRepository
import com.nolawiworkineh.core.data.networking.post
import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.EmptyDataResult
import io.ktor.client.HttpClient

// **AuthRepositoryImpl Class**: Implements the AuthRepository interface, providing the actual network logic for user registration.
class AuthRepositoryImpl(
    private val httpClient: HttpClient  // instance The HttpClient used to make network requests.
) : AuthRepository {

    // **register Function**: Registers a user by making an HTTP POST request to the backend API.
    override suspend fun register(
        email: String,  // The user's email
        password: String  // The user's password
    ): EmptyDataResult<DataError.Network> {
        // Makes a POST request to the /register endpoint with the user's email and password in the request body.
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",  // The endpoint for user registration
            body = RegisterRequest(email = email, password = password)  // The request body containing the email and password
        )
    }

    override suspend fun login(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {

    }
}
