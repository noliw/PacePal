package com.nolawiworkineh.core.data.run

import com.nolawiworkineh.core.domain.run.LocalRunDataSource
import com.nolawiworkineh.core.domain.run.RemoteRunDataSource
import com.nolawiworkineh.core.domain.run.Run
import com.nolawiworkineh.core.domain.run.RunId
import com.nolawiworkineh.core.domain.run.RunRepository
import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.EmptyDataResult
import com.nolawiworkineh.core.domain.util.Result
import com.nolawiworkineh.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class OfflineFirstRunRepository(
    private val localRunDataSource: LocalRunDataSource,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val applicationScope: CoroutineScope,
) : RunRepository {

    override fun getRuns(): Flow<List<Run>> {
        return localRunDataSource.getRuns()
    }

    // Function to fetch runs from the remote server and update the local database
    override suspend fun fetchRuns(): EmptyDataResult<DataError> {
        // Perform a remote fetch using the remoteRunDataSource
        return when (val result = remoteRunDataSource.getRuns()) {
            // If the remote fetch fails, return the error result
            is Result.Error -> result.asEmptyDataResult()

            // If the remote fetch succeeds, update the local database
            is Result.Success -> {
                // Use the application scope to ensure the operation is not tied to a UI lifecycle
                applicationScope.async {
                    // Insert the fetched runs into the local database
                    localRunDataSource.upsertRuns(result.data).asEmptyDataResult()
                }.await() // Wait for the coroutine to complete before returning
            }
        }
    }

    override suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyDataResult<DataError> {
        // Step 1: Attempt to save or update the run in the local database
        val localResult = localRunDataSource.upsertRun(run)

        // Step 2: Check if the local save operation was successful
        if (localResult !is Result.Success) {
            // If the local save fails, return the error as an EmptyResult
            return localResult.asEmptyDataResult()
        }

        // Step 3: Create a copy of the run with the ID generated from the local database
        val runWithId = run.copy(id = localResult.data)

        // Step 4: Attempt to save or update the run on the remote server, including the map picture
        val remoteResult = remoteRunDataSource.postRun(
            run = runWithId,         // The run data, now including the local database-generated ID
            mapPicture = mapPicture  // The map snapshot as a byte array
        )

        // Step 5: Handle the result of the remote save operation
        return when (remoteResult) {
            // If the remote save fails, we silently ignore it and return success
            is Result.Error -> {
                // Return success because the local operation was completed
                Result.Success(Unit)
            }

            // If the remote save succeeds, update the local database with the data returned by the server
            is Result.Success -> {
                applicationScope.async {
                    // Upsert the server's data into the local database for consistency
                    localRunDataSource.upsertRun(remoteResult.data).asEmptyDataResult()
                }.await() // Wait for the operation to complete before returning
            }
        }
    }


    override suspend fun deleteRun(id: RunId) {
        TODO("Not yet implemented")
    }
}
