package com.ineconnect.Componentes

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun Navegacion(
    pantallaActual: MutableState<String>,
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    when (pantallaActual.value) {
        "Bienvenida" -> {

            Button(onClick = { pantallaActual.value = "DatosPersonales" }) {
                Text("Registro del INE")
            }
        }
        "DatosPersonales" -> {
            DatosPersonales(onBackClick = onBackClick, onRegisterSuccess = onRegisterSuccess)
        }
    }
}
