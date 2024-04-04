package com.ineconnect.Componentes


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Canvas
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors.newSingleThreadExecutor
import com.google.firebase.storage.ktx.storage
import com.ineconnect.MainActivity
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import com.ineconnect.R

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DatosPersonales(
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit,
    navController: NavController

) {
    var nombreCompleto by remember { mutableStateOf(TextFieldValue()) }
    var fechaNacimiento by remember { mutableStateOf(TextFieldValue()) }
    var sexo by remember { mutableStateOf("Masculino") }
    var nacionalidad by remember { mutableStateOf(TextFieldValue()) }
    var lugarNacimiento by remember { mutableStateOf(TextFieldValue()) }
    var domicilio by remember { mutableStateOf(TextFieldValue()) }
    var claveElectoral by remember { mutableStateOf(TextFieldValue()) }
    var curp by remember { mutableStateOf(TextFieldValue()) }
    var path by remember { mutableStateOf(android.graphics.Path()) }
    var profileBitmap: Bitmap? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    var imagebitmap by remember{ mutableStateOf<Bitmap?>(null)}
    val launcer= rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
        uri: Uri? -> uri?.let {
            try{
                val inputStream=context.contentResolver.openInputStream(it)
                imagebitmap=BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception){
                Log.e("Error","Error al cargar la imagen",e)
            }
    }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item {
            TopAppBar(
                title = { Text(stringResource(id = R.string.tituloDatos)) },
                modifier = Modifier.fillMaxWidth()
            )
            Image(painter = painterResource(id = R.drawable.menuprincipal), contentDescription = "",modifier = Modifier.size(150.dp))
            Spacer(modifier = Modifier.height(16.dp))

        }
        item {
            Column(modifier = Modifier.padding(bottom = 10.dp)) {
                OutlinedTextField(
                    value = nombreCompleto,
                    onValueChange = { nombreCompleto = it },
                    label = { Text(stringResource(id = R.string.nombre_completo_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(2.dp))

                OutlinedTextField(
                    value = fechaNacimiento,
                    onValueChange = { fechaNacimiento = it },
                    label = { Text(stringResource(id = R.string.fecha_nacimiento_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(2.dp))

                val sexos = listOf("Masculino", "Femenino")
                var expanded by remember { mutableStateOf(false) }

                Box(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("Sexo: $sexo", modifier = Modifier.clickable { expanded = true })
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        sexos.forEach { genero ->
                            DropdownMenuItem(onClick = {
                                sexo = genero
                                expanded = false
                            },
                                text = { Text(text = genero) }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))

                OutlinedTextField(
                    value = nacionalidad,
                    onValueChange = { nacionalidad = it },
                    label = { Text(stringResource(id = R.string.nacionalidad_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(2.dp))

                OutlinedTextField(
                    value = lugarNacimiento,
                    onValueChange = { lugarNacimiento = it },
                    label = { Text(stringResource(id = R.string.lugar_nacimiento_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(2.dp))

                OutlinedTextField(
                    value = domicilio,
                    onValueChange = { domicilio = it },
                    label = { Text(stringResource(id = R.string.domicilio_label )) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(2.dp))

                OutlinedTextField(
                    value = claveElectoral,
                    onValueChange = { claveElectoral = it },
                    label = { Text(stringResource(id = R.string.clave_electoral_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(2.dp))

                OutlinedTextField(
                    value = curp,
                    onValueChange = { curp = it },
                    label = { Text(stringResource(id = R.string.curp_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        item{
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier.fillMaxWidth(),onClick = {
                launcer.launch("image/*")
            }) {
                Text(stringResource(id = R.string.fotoPerfil))
            }
        }
        item{
            imagebitmap?.let {
                Image(bitmap=it.asImageBitmap(),
                    contentDescription="Imagen seleccionada",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp))
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                guardarRegistroEnFirestore(
                    nombreCompleto.text,
                    fechaNacimiento.text,
                    sexo,
                    nacionalidad.text,
                    lugarNacimiento.text,
                    domicilio.text,
                    claveElectoral.text,
                    curp.text,
                    imagebitmap,
                    onSuccess = {
                       // Toast.makeText(context, "Registro exitoso / Successful", Toast.LENGTH_SHORT).show()
                        showAlerta(context,"Registro exitoso",R.drawable.principal)
                    },
                    onError = { errorMessage ->
                        //Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        showAlerta(context,errorMessage,R.drawable.principal)
                    },

                    )

                onRegisterSuccess()

            }) {
                Text(stringResource(id = R.string.register_button_Data))
            }


            Spacer(modifier = Modifier.height(2.dp))
            Button(modifier = Modifier.fillMaxWidth(), onClick = { //onBackClick
                navController.navigate("navegacion/{username}")
            }) {
                Text(stringResource(id = R.string.regresar))

            }
        }
    }
}



fun guardarRegistroEnFirestore(
    nombreCompleto: String,
    fechaNacimiento: String,
    sexo: String,
    nacionalidad: String,
    lugarNacimiento: String,
    domicilio: String,
    claveElectoral: String,
    curp : String,
    profileBitmap: Bitmap?,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit
) {
    val storageRef = Firebase.storage.reference
    var perfilUrl: String? = null

    profileBitmap?.let { bitmap ->
        val perfilImageRef = storageRef.child("perfil/${UUID.randomUUID()}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageData = baos.toByteArray()

        perfilImageRef.putBytes(imageData)
            .addOnSuccessListener {

                perfilImageRef.downloadUrl.addOnSuccessListener { uri ->
                    perfilUrl = uri.toString()

                    val db = Firebase.firestore
                    val registrosRef = db.collection("DatosPersonales")
                    val registro = hashMapOf(
                        "nombreCompleto" to nombreCompleto,
                        "fechaNacimiento" to fechaNacimiento,
                        "sexo" to sexo,
                        "nacionalidad" to nacionalidad,
                        "lugarNacimiento" to lugarNacimiento,
                        "domicilio" to domicilio,
                        "clave" to claveElectoral,
                        "curp" to curp,
                        "perfilUrl" to perfilUrl
                    )

                    registrosRef.add(registro)
                        .addOnSuccessListener { documentReference ->
                            println("Registro exitoso con ID: ${documentReference.id}")
                            onSuccess("Registro exitoso")
                        }
                        .addOnFailureListener { e ->
                            println("Error al registrar: $e")
                            onError("Error al registrar")
                        }
                }
            }
            .addOnFailureListener {
                onError("Error al subir la imagen")
            }
    } ?: run {
        val db = Firebase.firestore
        val registrosRef = db.collection("DatosPersonales")
        val registro = hashMapOf(
            "nombreCompleto" to nombreCompleto,
            "fechaNacimiento" to fechaNacimiento,
            "sexo" to sexo,
            "nacionalidad" to nacionalidad,
            "lugarNacimiento" to lugarNacimiento,
            "domicilio" to domicilio,
            "clave" to claveElectoral,
            "curp" to curp
        )

        registrosRef.add(registro)
            .addOnSuccessListener { documentReference ->
                println("Registro exitoso con ID: ${documentReference.id}")
                onSuccess("Registro exitoso")
            }
            .addOnFailureListener { e ->
                println("Error al registrar: $e")
                onError("Error al registrar")
            }
    }
}


