package com.ineconnect.Components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

import com.ineconnect.Views.RegistroViewModel


@Composable
fun Registro(viewModel: RegistroViewModel, navigateToLogin: () -> Unit, navigateToMenu: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember {mutableStateOf(false)}
    var errorMensaje by remember { mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Username") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        Button(onClick = {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                println("Intentando registrar usuario: $username")
                viewModel.registro(username, password, onSuccessRedirect = {
                    println("Registro exitoso")
                    navigateToMenu()
                }, onError = { errorMensaje -> println("Registro fallido") })
            } else {
                println("Campos vacíos")
                // Mostrar alerta de campos vacíos
            }
        }) {
            Text("Registro")
        }

        if(showError){
            Snackbar(modifier = Modifier.padding(16.dp),
                action = { Button(onClick = { showError = false}){
                    Text("Cerrar")
                } },{Text(text = errorMensaje)}
            ) {

            }
        }

        Button(onClick = { navigateToLogin() }) {
            Text("Inicio de sesión")
        }
    }
}
