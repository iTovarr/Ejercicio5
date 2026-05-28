package com.example.ejerciciolab10.ejerciciola109

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun MainPictogramaApp() {
    val navController = rememberNavController()
    val listaActividades = remember { mutableStateListOf<PictogramaModel>() }

    val servicio = remember {
        Retrofit.Builder()
            .baseUrl("https://api.arasaac.org/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PictogramaApiService::class.java)
    }

    NavHost(navController = navController, startDestination = "lista") {

        composable("lista") {
            ListaPictogramasScreen(navController, listaActividades)
        }

        composable("picNuevo") {
            FormularioPictogramaScreen(navController, listaActividades, pid = 0, servicio = servicio)
        }

        composable(
            route = "picEditar/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStack ->
            val id = backStack.arguments?.getInt("id") ?: 0
            FormularioPictogramaScreen(navController, listaActividades, pid = id, servicio = servicio)
        }

        composable(
            route = "picEliminar/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStack ->
            val id = backStack.arguments?.getInt("id") ?: 0
            SkinnerEliminarScreen(navController, listaActividades, pid = id)
        }
    }
}