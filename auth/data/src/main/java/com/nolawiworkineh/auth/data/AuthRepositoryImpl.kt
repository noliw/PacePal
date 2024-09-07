package com.nolawiworkineh.auth.data

import com.nolawiworkineh.auth.domain.AuthRepository
import com.nolawiworkineh.core.data.networking.post
import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.EmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
) : AuthRepository {
    override suspend fun register(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(email = email, password = password)
        )
    }
}