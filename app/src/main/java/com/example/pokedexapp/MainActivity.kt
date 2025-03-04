package com.example.pokedexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pokedexapp.network.RetrofitInstance
import com.example.pokedexapp.repository.PokemonRepository
import com.example.pokedexapp.viewmodel.PokemonViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.pokedexapp.ui.theme.PokemonListScreen

class PokemonViewModelFactory(private val repository: PokemonRepository) : Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = PokemonRepository(RetrofitInstance.api)
        val viewModelFactory = PokemonViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[PokemonViewModel::class.java]

        setContent {
            MaterialTheme {
                Surface {
                    PokemonListScreen(viewModel = viewModel)
                }
            }
        }
    }
}
