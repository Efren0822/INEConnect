package com.ineconnect

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ineconnect.Componentes.Login
import com.ineconnect.ui.theme.INEConnectTheme
import androidx.navigation.compose.*
import com.ineconnect.Componentes.bienvenida

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            INEConnectTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(navController)
                }
            }
        }
    }
}

@Composable
fun MainContent(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") {
            Login(
                onLoginClick = { username, password ->
                    println("Inicio de sesión exitoso")
                    println("Username: $username, Password: $password")
                },
                onRegisterClick = {
                    println("Abrir componente de registro")
                },
                onSuccessRedirect = { username ->navController.navigate("bienvenida/$username")

                }
            )
        }
        composable("bienvenida/{username}") { // Definir la ruta para la Bienvenida
            // Aquí se puede definir el componente de Bienvenida
            // Por ejemplo, podrías llamar a una función que representa este componente
            bienvenida()
        }
    }
}