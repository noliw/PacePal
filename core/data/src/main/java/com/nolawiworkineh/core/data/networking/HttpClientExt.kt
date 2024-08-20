package com.nolawiworkineh.core.data.networking


import com.nolawiworkineh.core.data.BuildConfig
import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException


suspend inline fun <reified Response: Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified Request, reified Response: Any> HttpClient.post(
    route: String,
    body: Request
): Result<Response, DataError.Network> {
    return safeCall {
        post {
            url(constructRoute(route))
            setBody(body)
        }
    }
}

// **Function delete**: Sends an HTTP DELETE request to remove a resource from the server.
suspend inline fun <reified Response: Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    // **Calls safeCall to execute the DELETE request safely**: Wraps the request in error handling.
    return safeCall {
        // **Constructs and sends the DELETE request**.
        delete {
            // **Sets the URL for the request**: Combines the base URL with the specified route.
            url(constructRoute(route))
            // **Adds query parameters to the request**: Includes additional data in the URL.
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}


// **Function safeCall**: Safely executes an HTTP request, handling any errors that occur.
suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError.Network> {
    // **Attempts to execute the HTTP request**: Tries to get a response from the server.
    val response = try {
        execute()
        // **Catches UnresolvedAddressException**: Handles cases where the device cannot resolve the server's address.
    } catch(e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET_CONNECTION)
        // **Catches SerializationException**: Handles cases where the response cannot be correctly parsed into the expected format.
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION_ERROR)
        // **Catches any other Exception**: Handles all other unexpected errors, except for CancellationException.
    } catch(e: Exception) {
        if(e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN_ERROR)
    }

    // **Converts the HTTP response to a Result object**: Uses the responseToResult function to handle the response.
    return responseToResult(response)
}



// **Converts an HTTP response to a Result object**: Handles success and various error cases.
suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Network>  {
    // **Checks the status code of the response**: Determines how to handle the response.
    return when(response.status.value) {
        // **Success case**: For status codes in the range 200-299, treats the response as successful.
        in 200..299 -> Result.Success(response.body<T>())
        // **Error case 401**: Unauthorized access, likely due to invalid credentials.
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        // **Error case 408**: Request timed out, indicating that the server took too long to respond.
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        // **Error case 409**: Conflict, often due to conflicting updates or data.
        409 -> Result.Error(DataError.Network.CONFLICT)
        // **Error case 413**: Payload too large, meaning the request was too big for the server to handle.
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        // **Error case 429**: Too many requests, indicating that the client has sent too many requests in a short time.
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        // **Error case 500-599**: Server error, indicating a problem on the server’s side.
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        // **Default case**: Any other status code is treated as an unknown error.
        else -> Result.Error(DataError.Network.UNKNOWN_ERROR)
    }
}


// **Function constructRoute**: Constructs a full URL based on the given route.
fun constructRoute(route: String): String {
    // **Checks if the route already contains the base URL**: If it does, return the route as is.
    return when {
        route.contains(BuildConfig.BASE_URL) -> route
        // **If the route starts with a "/"**, it appends the route to the base URL.
        route.startsWith("/") -> BuildConfig.BASE_URL + route
        // **Otherwise, it appends the route to the base URL, adding a "/" in front**.
        else -> BuildConfig.BASE_URL + "/$route"
    }
}
