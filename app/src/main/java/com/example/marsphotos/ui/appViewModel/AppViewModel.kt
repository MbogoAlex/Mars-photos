package com.example.marsphotos.ui.appViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.MarsApi
import com.example.marsphotos.ui.state.AppUiState
import com.example.marsphotos.ui.state.MarsUiState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AppViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(value = AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun loadPhotos() {
        _uiState.value = AppUiState(marsUiState = MarsUiState.Loading)
        viewModelScope.launch {
            _uiState.value = try {
                val photos = MarsApi.retrofitService.getPhotos()
                AppUiState(marsUiState = MarsUiState.Success(photos))
            } catch (e: IOException) {
                AppUiState(marsUiState = MarsUiState.Error)

            } catch (e: HttpException) {
                AppUiState(marsUiState = MarsUiState.Error)
            }
        }
    }

    init {
        loadPhotos()
    }
}