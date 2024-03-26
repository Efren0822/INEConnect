package com.ineconnect.Components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ineconnect.Views.MenuViewModel


@Composable
fun Menu1(viewModel: MenuViewModel, userId: String, navigateToDatosPersonales: () -> Unit) {
    var nombreCompleto by remember { mutableStateOf("") }
    var domicilio by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var curp by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.obtenerDatosUsuario(userId) { document ->
            if (document != null) {
                nombreCompleto = document.getString("nombreCompleto") ?: ""
                domicilio = document.getString("domicilio") ?: ""
                clave = document.getString("clave") ?: ""
                curp = document.getString("curp") ?: ""
                fechaNacimiento = document.getString("fechaNacimiento") ?: ""
                sexo = document.getString("sexo") ?: ""
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Mostrar datos del usuario o mensaje "No hay ningún dato disponible"
            Text(if (nombreCompleto.isNotEmpty()) nombreCompleto else "No hay ningún dato disponible")
            Text(if (domicilio.isNotEmpty()) domicilio else "")
            Text(if (clave.isNotEmpty()) clave else "")
            Text(if (curp.isNotEmpty()) curp else "")
            Text(if (fechaNacimiento.isNotEmpty()) fechaNacimiento else "")
            Icon(
                if (sexo == "M") Icons.Default.Face else Icons.Default.Person,
                contentDescription = "Sexo"
            )
        }
    }

    Button(onClick = { navigateToDatosPersonales() }) {
        Text("Agregar datos")
    }
}

