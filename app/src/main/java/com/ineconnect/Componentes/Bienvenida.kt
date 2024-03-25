package com.ineconnect.Componentes

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun bienvenida(navController: NavController, username: String) {
    // Contenido de tu componente bienvenida

    Button(onClick = {
        navController.navigate("navegacion/$username")
    }) {
        Text("Registro del INE")
    }
}