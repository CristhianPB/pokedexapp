# PokedexApp

## Descripción
Esta aplicación Android es una Pokédex que consume la API REST de [PokeAPI](https://pokeapi.co/) utilizando Retrofit para obtener una lista de Pokémon y sus imágenes. La interfaz se ha desarrollado con Jetpack Compose y se utiliza Coil para la carga de imágenes.

## Estructura del proyecto
- **Modelos**: Clases de datos en `PokemonModels.kt` para mapear la respuesta JSON.
- **Red**: `PokemonApi.kt` y `RetrofitInstance.kt` configuran la comunicación con la API.
- **Repository**: `PokemonRepository.kt` gestiona el acceso a los datos de la API.
- **ViewModel**: `PokemonViewModel.kt` maneja el estado y la lógica de negocio.
- **UI**: Composables en `PokemonListScreen.kt` muestran la lista de Pokémon, gestionando estados de carga y error.

## Funcionalidades
- **Consumo de API REST**: Utiliza Retrofit y Gson para obtener y mapear datos de la PokeAPI.
- **Patrón Repository**: Separa la lógica de datos.
- **ViewModel y manejo de estados**: Previene la pérdida de datos ante cambios de configuración.
- **Jetpack Compose**: Interfaz moderna y responsiva con LazyColumn para la lista.
- **Carga de imágenes con Coil**: Muestra imágenes oficiales de cada Pokémon.
- **Manejo de errores**: Indicador de carga y mensajes de error en caso de fallo.

## Cómo se consumen los datos
La aplicación realiza una solicitud GET al endpoint `https://pokeapi.co/api/v2/pokemon?limit=151` para obtener una lista de Pokémon. Los datos JSON se mapean a data classes de Kotlin utilizando Gson. El Repository se encarga de interactuar con la API y el ViewModel gestiona la actualización de la UI a través de estados.

