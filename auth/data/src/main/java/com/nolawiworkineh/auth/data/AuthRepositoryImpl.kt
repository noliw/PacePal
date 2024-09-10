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
    private val sessionStorage: SessionStorage
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

    // **login function**: Handles the login process by sending a network request and storing authentication data.
    override suspend fun login(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {

        // **Send Login Request**: Sends the email and password to the server using an HTTP POST request.
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",  // **API Endpoint**: The server route to send the login request to.
            body = LoginRequest(  // **Request Body**: The data (email and password) sent in the login request.
                email = email,  // **Email**: The user’s email.
                password = password  // **Password**: The user’s password.
            )
        )

        // **Check if Request Was Successful**: If the server responds with success, store the authentication info.
        if (result is Result.Success) {
            // **Store Auth Info**: Save the access token, refresh token, and user ID in session storage.
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,  // **Access Token**: The token used to authenticate API requests.
                    refreshToken = result.data.refreshToken,  // **Refresh Token**: Used to refresh the access token when it expires.
                    userId = result.data.userId  // **User ID**: The unique identifier for the authenticated user.
                )
            )
        }

        // **Return the Result**: Convert the result into an empty data result, whether success or error.
        return result.asEmptyDataResult()
    }
}
