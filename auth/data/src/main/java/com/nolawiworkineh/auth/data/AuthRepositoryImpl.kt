package com.nolawiworkineh.auth.data

import com.nolawiworkineh.auth.domain.AuthRepository
import com.nolawiworkineh.core.data.networking.post
import com.nolawiworkineh.core.domain.AuthInfo
import com.nolawiworkineh.core.domain.SessionStorage
import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.EmptyDataResult
import com.nolawiworkineh.core.domain.util.Result
import com.nolawiworkineh.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient

// **AuthRepositoryImpl Class**: Implements the AuthRepository interface, providing the actual network logic for user registration.
class AuthRepositoryImpl(
// instance The HttpClient used to make network requests.
    private val httpClient: HttpClient,
    // / **SessionStorage**: Stores and retrieves authentication info (e.g., tokens, user ID).
    private val SessionStorage: SessionStorage
) : AuthRepository {

    // **register Function**: Registers a user by making an HTTP POST request to the backend API.
    override suspend fun register(
        email: String,  // The user's email
        password: String  // The user's password
    ): EmptyDataResult<DataError.Network> {
        // Makes a POST request to the /register endpoint with the user's email and password in the request body.
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",  // The endpoint for user registration
            body = RegisterRequest(
                email = email,
                password = password
            )  // The request body containing the email and password
        )
    }

    override suspend fun login(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success) {
            SessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyDataResult()
    }
}
