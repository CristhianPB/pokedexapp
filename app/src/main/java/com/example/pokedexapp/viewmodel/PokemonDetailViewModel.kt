package com.example.pokedexapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.model.PokemonDetail
import com.example.pokedexapp.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val pokemonDetail: PokemonDetail) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

class PokemonDetailViewModel(
    private val repository: PokemonRepository,
    private val pokemonId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        fetchPokemonDetail()
    }

    private fun fetchPokemonDetail() {
        viewModelScope.launch {
            try {
                val detail = repository.getPokemonDetail(pokemonId)
                _uiState.value = DetailUiState.Success(detail)
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

class PokemonDetailViewModelFactory(
    private val repository: PokemonRepository,
    private val pokemonId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonDetailViewModel(repository, pokemonId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
