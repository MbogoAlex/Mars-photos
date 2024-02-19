package com.example.marsphotos.ui.state

import com.example.marsphotos.model.MarsPhoto

sealed interface MarsUiState {
    data class Success(val marsPhotos: List<MarsPhoto>): MarsUiState
    object Loading: MarsUiState
    object Error: MarsUiState
}

data class AppUiState(
    val marsUiState: MarsUiState = MarsUiState.Loading
)
