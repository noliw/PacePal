package com.nolawiworkineh.core.data.networking

import com.nolawiworkineh.core.data.BuildConfig
import com.nolawiworkineh.core.domain.AuthInfo
import com.nolawiworkineh.core.domain.SessionStorage
import com.nolawiworkineh.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

// **HttpClientFactory Class**: A factory class responsible for creating an instance of HttpClient.
class HttpClientFactory(
    private val sessionStorage: SessionStorage
) {

    // **build Function**: Creates and configures an instance of HttpClient.
    fun build(): HttpClient {
        // CIO engine is used to make non-blocking HTTP requests.
        return HttpClient(CIO) {

            // **ContentNegotiation**: This is installed to handle JSON serialization and deserialization.
            install(ContentNegotiation) {
                // this is the default JSON configuration
                json(
                    // here we can configure the JSON serializer
                    json = Json {
                        // Allows the client to ignore unknown fields in JSON responses.
                        ignoreUnknownKeys = true
                    }
                )
            }

            // **Logging**: Logs all HTTP request and response information using Timber for debugging purposes.
            install(Logging) {
                // A custom logger that uses Timber for logging.
                logger = object : Logger {
                    // function to log messages
                    override fun log(message: String) {
                        Timber.d(message)  // Logs the message using Timber's debug level.
                    }
                }
                level = LogLevel.ALL  // Logs all HTTP details: headers, bodies, etc.
            }

            // **defaultRequest Block**: Adds default settings to every request made with this client.
            defaultRequest {
                contentType(ContentType.Application.Json)  // Ensures all requests expect JSON.
                header(
                    "x-api-key",
                    BuildConfig.API_KEY
                )  // Adds the API key from BuildConfig to every request.
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val info = sessionStorage.get()
                        BearerTokens(
                            accessToken = info?.accessToken ?: "",
                            refreshToken = info?.refreshToken ?: ""
                        )
                    }
                    refreshTokens {
                        val info = sessionStorage.get()
                        val response = client.post<AccessTokenRequest, AccessTokenResponse>(
                            route = "/accessToken",
                            body =AccessTokenRequest(
                                refreshToken = info?.refreshToken ?: "",
                                userId = info?.userId ?: ""
                            )
                        )
                        if (response is Result.Success ) {
                            val newAuthInfo = AuthInfo(
                                accessToken = response.data.accessToken,
                                refreshToken = info?.refreshToken ?: "",
                                userId = info?.userId ?: ""
                            )
                            sessionStorage.set(newAuthInfo)

                            BearerTokens(
                                accessToken = newAuthInfo.accessToken,
                                refreshToken = newAuthInfo.refreshToken
                            )
                        } else {
                            BearerTokens(
                                accessToken = "",
                                refreshToken = ""
                            )
                        }
                    }
                }
            }

        }
    }
}
