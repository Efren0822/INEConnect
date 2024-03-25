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
import com.ineconnect.Components.LoginC
import com.ineconnect.Components.Menu
import com.ineconnect.Views.LoginViewModel
import com.ineconnect.Componentes.*


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
                   // MiApp()
                }
            }
        }
    }
}


@Composable
fun MainContent(navController: NavHostController) {

    val pantallaActual = remember { mutableStateOf("registro") }
    NavHost(navController, startDestination = "registro") {
        composable("login") {
            Login(
                onLoginClick = { username, password ->
                    println("Inicio de sesión exitoso")
                    println("Username: $username, Password: $password")
                },
                onRegisterClick = { navController ->
                    println("Abrir componente de registro")
                    navController.navigate("registro")
                },
                onSuccessRedirect = { username ->
                    pantallaActual.value = "Bienvenida"
                    navController.navigate("bienvenida/$username")
                }
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
            Navegacion(
                pantallaActual = pantallaActual,
                onBackClick = { navController.popBackStack() },
                onRegisterSuccess = {
                    println("Registro exitoso desde Navegacion")
                    navController.popBackStack()
                }
            )

        }
    }
}


/*
@Preview
@Composable
fun MiApp(){
    val navControlador = rememberNavController()
    NavHost(navController = navControlador, startDestination = "login"){
        composable("login"){
            LoginScreen(navController = navControlador)
        }
    }
}



@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    val KEY_ROUTE = "route"

    LoginC(
        viewModel = viewModel,
        onSuccessRedirect = {
            navController.navigate("Componentes/menu") {
                // Aquí puedes configurar transiciones o animaciones si lo deseas
            }
        },
        onError = { errorMessage ->
            // Mostrar alerta de error con el mensaje
        },
        navController = navController
    )

    navController.currentBackStackEntry?.let { backStackEntry ->
        val arguments = backStackEntry.arguments
        val currentRoute = arguments?.getString(KEY_ROUTE)

        if (currentRoute == "registro") {
            navController.navigate("Componentes/registro") {
                // Aquí puedes configurar transiciones o animaciones si lo deseas
            }
        }
    }
}

 */