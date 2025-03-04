package com.example.pokedexapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pokedexapp.model.Pokemon
import com.example.pokedexapp.util.extractPokemonId
import com.example.pokedexapp.viewmodel.PokemonViewModel
import com.example.pokedexapp.viewmodel.UiState

@Composable
fun PokemonListScreen(viewModel: PokemonViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Pokedex") })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Error -> {
                    Text(
                        text = (uiState as UiState.Error).message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is UiState.Success -> {
                    val pokemonList = (uiState as UiState.Success).pokemonList
                    LazyColumn {
                        items(pokemonList) { pokemon ->
                            PokemonItem(pokemon = pokemon)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: Pokemon) {
    val id = extractPokemonId(pokemon.url)
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = pokemon.name,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = pokemon.name.replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.h6)
    }
}
