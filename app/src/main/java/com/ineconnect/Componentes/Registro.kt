package com.ineconnect.Componentes

import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ineconnect.R

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class User(
    val username: String = "",
    val password: String = ""
)

fun registerUser(username: String, password: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
    val db = Firebase.firestore
    val usersRef = db.collection("Usuarios")


    usersRef.whereEqualTo("username", username).get()
        .addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                val user = User(username, password)
                usersRef.add(user)
                    .addOnSuccessListener {
                        onSuccess(username)
                    }
                    .addOnFailureListener { exception ->
                        onError("Error al registrar el usuario: ${exception.message}")
                    }
            } else {

                onError("El usuario ya existe")
            }
        }
        .addOnFailureListener { exception ->
            onError("Error al buscar el usuario: ${exception.message}")
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(navController: NavController, onBackClick: () -> Unit) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item{

            TopAppBar(title = { Text(stringResource(id = R.string.registro)) },
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Image(painter = painterResource(id = R.drawable.menuprincipal), contentDescription = "",modifier = Modifier.size(150.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.textRegistro))
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(id = R.string.username2_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(id = R.string.password2_label)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                registerUser(username.text, password.text,
                    onSuccess = {
                        //Toast.makeText(context, "Registro exitoso / Successful", Toast.LENGTH_SHORT).show()
                                showAlerta(context,"Registro exitoso",R.drawable.principal)
                    },
                    onError = { errorMessage ->
                        //Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        showAlerta(context,errorMessage,R.drawable.principal)
                    }
                )
            }) {
                Text(stringResource(id = R.string.register2_button_label))
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                navController.navigate("login")
            }) {
                Text(stringResource(id = R.string.go_to_login_button_label))
            }
        }
    }
}
