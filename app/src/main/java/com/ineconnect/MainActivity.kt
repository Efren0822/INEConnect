import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ineconnect.Componentes.Registro
import com.ineconnect.Componentes.Login
import com.ineconnect.ui.theme.INEConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            INEConnectTheme {
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
    val registro = Registro(
        nombreCompleto = "Juan Pérez",
        fechaNacimiento = "01/01/1990",
        sexo = "Masculino",
        nacionalidad = "Mexicana",
        lugarNacimiento = "Ciudad de México",
        domicilio = "Calle Principal #123",
        claveElectoral = "ABCDE1234567890",
        fotografia = null,
        firma = null
    )

    Login(
        onLoginClick = { username, password ->
            println("Inicio de sesión exitoso")
            println("Username: $username, Password: $password")
        },
        onRegisterClick = {
            println("Abrir componente de registro")
        },
        onSuccessRedirect = { username ->
            println("Redirigiendo a la pantalla principal para el usuario $username")
            // Aquí podrías realizar la navegación a la pantalla principal
        }
    )
}
