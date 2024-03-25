package com.ineconnect.Components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ineconnect.Views.LoginViewModel







@Composable
fun LoginC(
    viewModel: LoginViewModel,
    onSuccessRedirect: () -> Unit,
    onError: (String) -> Unit,
    navController: NavController
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember {mutableStateOf(false)}
    var errorMensaje by remember { mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Button(onClick = {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                println("Intentando iniciar sesión con usuario: $username")
                viewModel.login(username, password, onSuccessRedirect = {
                    println("Inicio de sesión exitoso")
                    navigateToMenu(navController)
                }, onError = {errorMensaje -> println("Inicio de sesión fallido")})

            } else {
                println("Campos vacíos")

            }
        }) {
            Text("Inicio de sesión")
        }
        if(showError){
            Snackbar(modifier = Modifier.padding(16.dp),
                action = { Button(onClick = { showError = false}){
                    Text("Cerrar")
                } },{Text(text = errorMensaje)}
            ) {

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            println("Redirigiendo al componente de Registro")
            navigateToRegistro(navController)
        }) {
            Text("Registro de usuario")
        }
    }


}
fun navigateToRegistro(navController: NavController) {
    navController.navigate("registro") {
        // Aquí puedes configurar transiciones o animaciones si lo deseas
    }
}

// Función para navegar al componente de Menú
fun navigateToMenu(navController: NavController) {
    navController.navigate("menu") {
        // Aquí puedes configurar transiciones o animaciones si lo deseas
    }
}
