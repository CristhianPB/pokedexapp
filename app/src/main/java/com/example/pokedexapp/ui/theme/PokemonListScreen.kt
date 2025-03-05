package com.example.pokedexapp.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokedexapp.model.Pokemon
import com.example.pokedexapp.util.extractPokemonId
import com.example.pokedexapp.repository.PokemonRepository
import com.example.pokedexapp.viewmodel.PokemonViewModel
import com.example.pokedexapp.viewmodel.UiState
import com.example.pokedexapp.viewmodel.PokemonViewModelFactory

@Composable
fun PokemonListScreen(
    navController: NavController,
    repository: PokemonRepository
) {
    val viewModel: PokemonViewModel = viewModel(
        factory = PokemonViewModelFactory(repository)
    )
    val uiState by viewModel.uiState.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Pokedex") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar Pokémon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxSize()) {
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
                        val filteredList = if (searchQuery.isEmpty()) pokemonList
                        else pokemonList.filter { it.name.contains(searchQuery, ignoreCase = true) }

                        LazyColumn {
                            items(filteredList) { pokemon ->
                                PokemonItem(pokemon = pokemon, navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: Pokemon, navController: NavController) {
    val id = extractPokemonId(pokemon.url)
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("detail/$id") }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
                contentDescription = pokemon.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.h6
            )
        }
    }
}