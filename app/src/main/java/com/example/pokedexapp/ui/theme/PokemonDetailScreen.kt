package com.example.pokedexapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pokedexapp.repository.PokemonRepository
import com.example.pokedexapp.viewmodel.DetailUiState
import com.example.pokedexapp.viewmodel.PokemonDetailViewModel
import com.example.pokedexapp.viewmodel.PokemonDetailViewModelFactory
import androidx.navigation.NavController

@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    navController: NavController,
    repository: PokemonRepository
) {
    val viewModel: PokemonDetailViewModel = viewModel(
        factory = PokemonDetailViewModelFactory(repository, pokemonId)
    )
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Pokémon") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (uiState) {
                is DetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is DetailUiState.Error -> {
                    Text(
                        text = (uiState as DetailUiState.Error).message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is DetailUiState.Success -> {
                    val detail = (uiState as DetailUiState.Success).pokemonDetail
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Imagen del Pokémon usando Coil
                        AsyncImage(
                            model = detail.sprites.other.officialArtwork.imageUrl,
                            contentDescription = detail.name,
                            modifier = Modifier
                                .size(200.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = detail.name.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "ID: ${detail.id}")
                        Text(text = "Altura: ${detail.height}")
                        Text(text = "Peso: ${detail.weight}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Tipos:", style = MaterialTheme.typography.h6)
                        detail.types.forEach { typeSlot ->
                            Text(text = "- ${typeSlot.type.name.replaceFirstChar { it.uppercase() }}")
                        }
                    }
                }
            }
        }
    }
}
