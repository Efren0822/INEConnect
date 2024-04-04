package com.ineconnect.Components

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ineconnect.Views.MenuViewModel
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.graphics.asImageBitmap
//import coil.compose.rememberImagePainter
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.ineconnect.MainActivity
import com.ineconnect.R
import java.util.Locale
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navegacion(viewModel: MenuViewModel, userId: String, navigateToDatosPersonales: () -> Unit,navigateToLogin:()->Unit) {
    val datosList = remember { mutableStateListOf<Map<String, Any>>() }
    val context = LocalContext.current
    val calendario = Calendar.getInstance()
    val hora = calendario.get(Calendar.HOUR_OF_DAY)
    val username = userId


    val saludos = when(hora){
        in 0..11 ->R.string.dias
        in 12..19 -> R.string.tardes
        else -> R.string.noches
    }
    val saludo = stringResource(id = saludos)
    val dat = stringResource(id = R.string.no_data)


    val welcomeMessage = String.format(stringResource(id = R.string.menu),username,saludo)

    LaunchedEffect(Unit) {
        val datos = viewModel.obtenerDatosDeColeccion()
        datosList.addAll(datos.map { it.mapValues { entry -> entry.value.toString() } })
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item{

            TopAppBar(
                title = { Text(welcomeMessage) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(5.dp))
            //Image(painter = painterResource(id = R.drawable.menuprincipal), contentDescription = "",modifier = Modifier.size(150.dp))
            Spacer(modifier = Modifier.height(5.dp))
        }
        item{
                Button(onClick = { changeAppLanguage(context, "en")
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { }
                ) {
                    Text("Switch to English")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        changeAppLanguage(context, "es")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { }
                ) {
                    Text("Cambiar a español")

            }

        }
        items(datosList) { datos ->
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(stringResource(id = R.string.perfil))
                    Spacer(modifier = Modifier
                        .height(8.dp)
                        .align(AbsoluteAlignment.Left))
                    val imageUrl= datos["perfilUrl"] as? String
                    if(imageUrl != null){
                        imageUrl?.let {
                            Image(painter = rememberImagePainter(imageUrl), contentDescription = "Perfil",
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(8.dp))
                        }

                    }
                    val nombreCompleto = String.format(stringResource(id = R.string.nombre),datos["nombreCompleto"]?:dat)
                    Text(nombreCompleto)
                    //Text("Nombre: ${datos["nombreCompleto"] ?: "No hay ningún dato disponible"}")
                    Spacer(modifier = Modifier.height(8.dp))
                    //Text("Domicilio: ${datos["domicilio"] ?: ""}")
                    val curp = String.format(stringResource(id = R.string.curp),datos["curp"]?:dat)
                    Text(curp)
                    Spacer(modifier = Modifier.height(8.dp))
                    //Text("Clave: ${datos["clave"] ?: ""}")
                    val clave = String.format(stringResource(id = R.string.clave),datos["clave"]?:dat)
                    Text(clave)
                    Spacer(modifier = Modifier.height(8.dp))
                    //Text("CURP: ${datos["curp"] ?: ""}")
                    val domicilio = String.format(stringResource(id = R.string.domicilio),datos["domicilio"]?:dat)
                    Text(domicilio)
                    Spacer(modifier = Modifier.height(8.dp))
                    //Text("Fecha de Nacimiento: ${datos["fechaNacimiento"] ?: ""}")
                    val fechaN = String.format(stringResource(id = R.string.fechaNacimiento),datos["fechaNacimiento"]?:dat)
                    Text(fechaN)
                    Spacer(modifier = Modifier.height(8.dp))
                    //Text("Sexo: ${datos["sexo"] ?: ""}")
                    val lugarN = String.format(stringResource(id = R.string.lugarNacimiento),datos["lugarNacimiento"]?:dat)
                    Text(lugarN)
                    Spacer(modifier = Modifier.height(8.dp))
                    val sexo = String.format(stringResource(id = R.string.sexo),datos["sexo"]?:dat)
                    Text(sexo)
                    Icon(
                        if (datos["sexo"] == "Masculino" || datos["sexo"] == "masculino" || datos["sexo"] == "Hombre" || datos["sexo"] == "hombre" || datos["sexo"] == "M" || datos["sexo"] == "m") Icons.Default.Person else Icons.Default.Favorite,
                        contentDescription = "Sexo"
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = { navigateToDatosPersonales() },modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(id = R.string.add_data_button_label))
                }
                Button(onClick = { navigateToLogin() },modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(id = R.string.logout))
                }
            }
        }
    }
}

fun base64ToBitmap1(base64String: String): Bitmap?{
    val decodeBytes: ByteArray = Base64.decode(base64String,Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodeBytes,0,decodeBytes.size)
}


fun changeAppLanguage(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)
    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)


    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}
