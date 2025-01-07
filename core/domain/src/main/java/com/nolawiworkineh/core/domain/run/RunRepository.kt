package com.nolawiworkineh.core.domain.run

import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface RunRepository {
    // Provides a stream of runs that updates in real-time as changes occur in the data sources
    // (e.g., new runs are saved, old runs are deleted).
    fun getRuns(): Flow<List<Run>>
    // Explicitly fetches runs from the remote server and updates the local database with the results.
    suspend fun fetchRuns(): EmptyDataResult<DataError>
    // Saves or updates a run in the local database and synchronizes it with the remote server.
    suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyDataResult<DataError>
    // Deletes a specific run from both the local database and the remote server.
    suspend fun deleteRun(id: RunId)
}