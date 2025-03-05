package com.example.pokedexapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.model.PokemonFullDetail
import com.example.pokedexapp.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class FullDetailUiState {
    object Loading : FullDetailUiState()
    data class Success(val fullDetail: PokemonFullDetail) : FullDetailUiState()
    data class Error(val message: String) : FullDetailUiState()
}

class PokemonFullDetailViewModel(
    private val repository: PokemonRepository,
    private val pokemonId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<FullDetailUiState>(FullDetailUiState.Loading)
    val uiState: StateFlow<FullDetailUiState> = _uiState

    init {
        fetchFullDetail()
    }

    private fun fetchFullDetail() {
        viewModelScope.launch {
            try {
                val fullDetail = repository.getPokemonFullDetail(pokemonId)
                _uiState.value = FullDetailUiState.Success(fullDetail)
            } catch (e: Exception) {
                _uiState.value = FullDetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

class PokemonFullDetailViewModelFactory(
    private val repository: PokemonRepository,
    private val pokemonId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonFullDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonFullDetailViewModel(repository, pokemonId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
