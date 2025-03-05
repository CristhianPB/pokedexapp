package com.example.pokedexapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokedexapp.repository.PokemonRepository
import com.example.pokedexapp.viewmodel.FullDetailUiState
import com.example.pokedexapp.viewmodel.PokemonFullDetailViewModel
import com.example.pokedexapp.viewmodel.PokemonFullDetailViewModelFactory

@Composable
fun PokemonFullDetailScreen(
    pokemonId: Int,
    navController: NavController,
    repository: PokemonRepository
) {
    val viewModel: PokemonFullDetailViewModel = viewModel(factory = PokemonFullDetailViewModelFactory(repository, pokemonId))
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle Completo") },
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
                is FullDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is FullDetailUiState.Error -> {
                    Text(
                        text = (uiState as FullDetailUiState.Error).message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is FullDetailUiState.Success -> {
                    val fullDetail = (uiState as FullDetailUiState.Success).fullDetail
                    val detail = fullDetail.detail
                    val species = fullDetail.species

                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        // Imagen
                        Card(
                            elevation = 8.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            AsyncImage(
                                model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${detail.id}.png",
                                contentDescription = detail.name,
                                modifier = Modifier
                                    .size(200.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Información básica
                        Card(
                            elevation = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = detail.name.replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.h4,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Text(text = "ID: ${detail.id}")
                                Text(text = "Altura: ${detail.height}")
                                Text(text = "Peso: ${detail.weight}")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Abilities
                        Card(
                            elevation = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Abilities:", style = MaterialTheme.typography.h6)
                                detail.abilities.forEach { ability ->
                                    val abilityColor =
                                        if (ability.is_hidden) Color.Magenta else MaterialTheme.colors.onSurface
                                    Text(
                                        text = "- ${ability.ability.name} ${if (ability.is_hidden) "(Hidden)" else ""}",
                                        color = abilityColor
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats
                        Card(
                            elevation = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Stats:", style = MaterialTheme.typography.h6)
                                detail.stats.forEach { stat ->
                                    val statColor = when {
                                        stat.base_stat >= 100 -> Color(0xFF4CAF50) // Verde para stat alta
                                        stat.base_stat < 50 -> Color(0xFFF44336)  // Rojo para stat baja
                                        else -> MaterialTheme.colors.onSurface
                                    }
                                    Text(
                                        text = "- ${stat.stat.name}: ${stat.base_stat}",
                                        color = statColor
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Tipos
                        Card(
                            elevation = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Types:", style = MaterialTheme.typography.h6)
                                detail.types.forEach { typeSlot ->
                                    Text(text = "- ${typeSlot.type.name}")
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Grupo huevo
                        Card(
                            elevation = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Egg Groups:", style = MaterialTheme.typography.h6)
                                (species.egg_groups ?: emptyList()).forEach { egg ->
                                    Text(text = "- ${egg.name}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
