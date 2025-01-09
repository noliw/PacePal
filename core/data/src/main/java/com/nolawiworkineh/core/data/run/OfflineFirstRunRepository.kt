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
        TODO("Not yet implemented")
    }

    override suspend fun deleteRun(id: RunId) {
        TODO("Not yet implemented")
    }
}
