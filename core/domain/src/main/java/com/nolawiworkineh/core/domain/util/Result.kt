package com.nolawiworkineh.core.domain.util

// this should return a success or failure
// This sealed interface represents a result that can either be a success with data or an error.
sealed interface Result<out D, out E : Error> {

    // This data class represents a successful result containing the desired data.
    data class Success<out D>(val data: D) : Result<D, Nothing>

    // This data class represents an error result containing the error details.
    data class Error<out E : com.nolawiworkineh.core.domain.util.Error>(val error: E) : Result<Nothing, E>
}

// This extension function maps a successful result to a new result with transformed data.
inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    // If the result is successful, apply the mapping function to the data.
    return when (this) {
        is Result.Success -> Result.Success(map(data))
        // If the result is an error, just return the error unchanged.
        is Result.Error -> Result.Error(error)
    }
}

// This function converts a successful result into an empty result (no data, just success).
fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyDataResult<E> {
    // Use the map function to convert the success data to Unit, indicating an empty result.
    return map { Unit }
}

// This type alias defines a Result type where success is represented by Unit (no data) and errors are of type E.
typealias EmptyDataResult<E> = Result<Unit, E>

