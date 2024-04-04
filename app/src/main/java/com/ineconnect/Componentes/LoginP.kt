@file:Suppress("UNUSED_EXPRESSION")

package com.ineconnect.Componentes

import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.content.Context
import android.widget.Toast
import android.widget.Toast.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ineconnect.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(onLoginClick: (String, String) -> Unit, onRegisterClick: (NavController) -> Unit, onSuccessRedirect: (String) -> Unit,navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item{
            TopAppBar(title = { Text(stringResource(id = R.string.login)) },
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Image(painter = painterResource(id = R.drawable.login), contentDescription = "", modifier = Modifier.size(150.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(id = R.string.textlogin))
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(id = R.string.username_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(id = R.string.password_label)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                validateUser(username, password, onSuccess = {
                    onLoginClick(username, password)
                    onSuccessRedirect(username)
                    //makeText(context, "Inicio de sesion exitoso / Successful", LENGTH_SHORT).show()
                    showAlerta(context,"Inicio de sesión exitoso",R.drawable.principal)
                }, onError = { errorMessage ->
                    //Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    showAlerta(context,errorMessage,R.drawable.principal)
                })
            }) {
                Text(stringResource(id = R.string.login_button_label))
            }
        }
        item{
        Spacer(modifier = Modifier.height(8.dp))
        Button(modifier = Modifier.fillMaxWidth(),onClick = { //onRegisterClick
            navController.navigate("registro")
        }) {
            Text(stringResource(id = R.string.register_button_label))
        }
        }
    }
}

fun showErrorDialog(context: Context, errorMessage: String) {
    val dialogBuilder = AlertDialog.Builder(context)
    dialogBuilder.setMessage(errorMessage)
        .setCancelable(false)
        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
    val alert = dialogBuilder.create()
    alert.setTitle("Error")
    alert.show()
}

fun validateUser(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
    val db = Firebase.firestore
    val usersRef = db.collection("Usuarios")
    println("Contectado a firebase")
    usersRef.whereEqualTo("username", username).get()
        .addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                onError("Usuario no encontrado")
            } else {
                val userDoc = documents.documents.first()
                val actualPassword = userDoc.getString("password")
                if (actualPassword == password) {
                    onSuccess()
                } else {
                    onError("Contraseña incorrecta")
                }
            }
        }
        .addOnFailureListener { exception ->
            onError("Error al iniciar sesión: ${exception.message}")
        }
}

