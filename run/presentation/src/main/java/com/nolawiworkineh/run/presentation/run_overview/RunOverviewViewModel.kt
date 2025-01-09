package com.nolawiworkineh.run.presentation.run_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.core.domain.run.RunRepository
import com.nolawiworkineh.run.presentation.run_overview.mappers.toRunUi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


// ViewModel for the Run Overview screen
class RunOverviewViewModel(
    private val runRepository: RunRepository // Injects the RunRepository dependency
) : ViewModel() {

    // State that holds the UI data for the Run Overview screen
    var state by mutableStateOf(RunOverviewState())
        private set // Private setter ensures state can only be modified within the ViewModel

    // Initialize the ViewModel by fetching and observing run data
    init {
        // Observe the runs from the repository and update the state with the UI representation
        runRepository.getRuns().onEach { runs ->
            val runsUi = runs.map { it.toRunUi() } // Map domain runs to UI runs
            state = state.copy(runs = runsUi) // Update the state with the transformed runs
        }.launchIn(viewModelScope) // Launch in the lifecycle-aware ViewModel scope

        // Fetch runs from the remote server to ensure local and remote data are in sync
        viewModelScope.launch {
            runRepository.fetchRuns()
        }
    }

    // Handle user actions from the Run Overview screen
    fun onAction(action: RunOverviewAction) {
        when (action) {
            RunOverviewAction.OnLogoutClick -> Unit // Placeholder for logout logic
            RunOverviewAction.OnStartRunClick -> Unit // Placeholder for starting a run
            is RunOverviewAction.DeleteRun -> {
                // Handle run deletion
                viewModelScope.launch {
                    runRepository.deleteRun(action.runUi.id) // Delete the run by its ID
                }
            }
            else -> Unit // Handle any other actions if needed
        }
    }
}
