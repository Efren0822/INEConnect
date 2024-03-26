package com.ineconnect.Componentes

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun bienvenida(navController: NavController, username: String) {


    Button(onClick = {
        navController.navigate("datos")
    }) {
        Text("Registro del INE")
    }
}