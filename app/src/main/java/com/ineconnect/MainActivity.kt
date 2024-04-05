package com.ineconnect

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ineconnect.Componentes.Login
import com.ineconnect.Componentes.bienvenida
import com.ineconnect.ui.theme.INEConnectTheme

import com.ineconnect.Views.LoginViewModel
import com.ineconnect.Componentes.*
import com.ineconnect.Components.Navegacion
import com.ineconnect.Views.MenuViewModel
import com.ineconnect.Views.RegistroViewModel


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
    val pantallaActual = remember { mutableStateOf("registro") }
    NavHost(navController, startDestination = "login") {
        composable("login") {
            Login(
                onLoginClick = { username, password ->
                    println("Inicio de sesión exitoso")
                    println("Username: $username, Password: $password")
                },
                onRegisterClick = { navController ->
                    println("Abrir componente de registro")
                    navController.navigate("Registro")
                },
                onSuccessRedirect = { username ->
                    pantallaActual.value = "navegacion"
                    navController.navigate("navegacion/$username")
                },
                navController=navController
            )
        }
        composable("bienvenida/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            bienvenida(navController, username)
        }

        composable("registro") {
            Registro(navController = navController) {
                navController.popBackStack()
            }
        }
        composable("navegacion/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            Navegacion(viewModel = MenuViewModel(), userId = username, navigateToDatosPersonales = { navController.navigate("datos") }) {
                navController.navigate("login")
            }

        }
        composable("datos"){
            DatosPersonales(
                onBackClick = { navController.navigate("navegacion/{username}") },
                onRegisterSuccess = { navController.navigate("navegacion/{username}") },
                navController=navController
            )
        }
    }
}


