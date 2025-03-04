package com.example.pokedexapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.model.Pokemon
import com.example.pokedexapp.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Estados de la UI
sealed class UiState {
    object Loading : UiState()
    data class Success(val pokemonList: List<Pokemon>) : UiState()
    data class Error(val message: String) : UiState()
}

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                val list = repository.getPokemonList(151)
                _uiState.value = UiState.Success(list)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
