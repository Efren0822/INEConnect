package com.ineconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ineconnect.Componentes.Registro  // Importa la clase Registro desde el paquete Componentes
import com.ineconnect.Componentes.Login
import com.ineconnect.ui.theme.INEConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            INEConnectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent() {
    // Crear una instancia de Registro
    val registro = Registro(
        nombreCompleto = "Juan Pérez",
        fechaNacimiento = "01/01/1990",
        sexo = "Masculino",
        nacionalidad = "Mexicana",
        lugarNacimiento = "Ciudad de México",
        domicilio = "Calle Principal #123",
        claveElectoral = "ABCDE1234567890",
        fotografia = null, // Aquí deberías asignar la imagen de la fotografía
        firma = null // Aquí deberías asignar la imagen de la firma
    )

    Login(
        onLoginClick = { username, password ->
            println("Inicio de sesión exitoso")
            println("Username: $username, Password: $password")
        },
        onRegisterClick = {
            // Aquí puedes utilizar la instancia de registro si es necesario
            println("Abrir componente de registro")
        },
    )
}
