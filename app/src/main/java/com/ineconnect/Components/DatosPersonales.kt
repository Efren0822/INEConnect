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
import com.ineconnect.Views.DatosPersonalesViewModel


@Composable
fun DatosPersonales1(viewModel: DatosPersonalesViewModel, userId: String, navigateToMenu: () -> Unit) {
    var nombreCompleto by remember { mutableStateOf("") }
    var domicilio by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var curp by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }
    var showError by remember {mutableStateOf(false)}
    var errorMensaje by remember { mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = nombreCompleto,
            onValueChange = { nombreCompleto = it },
            placeholder = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        TextField(
            value = domicilio,
            onValueChange = { domicilio = it },
            placeholder = { Text("Domicilio") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        TextField(
            value = clave,
            onValueChange = { clave = it },
            placeholder = { Text("Clave") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        TextField(
            value = curp,
            onValueChange = { curp = it },
            placeholder = { Text("CURP") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        TextField(
            value = fechaNacimiento,
            onValueChange = { fechaNacimiento = it },
            placeholder = { Text("Fecha de nacimiento") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        TextField(
            value = sexo,
            onValueChange = { sexo = it },
            placeholder = { Text("Sexo (M/F)") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        Button(onClick = {
            viewModel.guardarDatos(userId, nombreCompleto, domicilio, clave, curp, fechaNacimiento, sexo) { isSuccess ->
                if (isSuccess) navigateToMenu()
                else {
                    showError = true
                    errorMensaje="Inicio de sesion incorrecto"}
            }
        }) {
            Text("Guardar datos")
        }
        if(showError){
            Snackbar(modifier = Modifier.padding(16.dp),
                action = { Button(onClick = { showError = false}){
                    Text("Cerrar")
                } },{Text(text = errorMensaje)}
            ) {

            }
        }
    }
}
