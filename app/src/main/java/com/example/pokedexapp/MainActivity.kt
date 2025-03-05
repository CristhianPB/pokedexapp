package com.example.pokedexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedexapp.network.RetrofitInstance
import com.example.pokedexapp.repository.PokemonRepository
import com.example.pokedexapp.ui.theme.PokemonFullDetailScreen
import com.example.pokedexapp.ui.theme.PokemonListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = PokemonRepository(RetrofitInstance.api)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            PokemonListScreen(navController = navController, repository = repository)
                        }
                        composable("detail/{pokemonId}") { backStackEntry ->
                            val pokemonId = backStackEntry.arguments?.getString("pokemonId")?.toIntOrNull()
                            if (pokemonId != null) {
                                PokemonFullDetailScreen(
                                    pokemonId = pokemonId,
                                    navController = navController,
                                    repository = repository
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
