package com.ineconnect.Componentes

import android.graphics.Bitmap

class Registro(
    var nombreCompleto: String,
    var fechaNacimiento: String,
    var sexo: String,
    var nacionalidad: String,
    var lugarNacimiento: String,
    var domicilio: String,
    var claveElectoral: String,
    var fotografia: Bitmap?,
    var firma: Bitmap?
) {
    fun mostrarRegistro() {
        println("------------------------------")
        println("|        INSTITUTO NACIONAL ELECTORAL        |")
        println("------------------------------")
        println("Nombre completo: $nombreCompleto")
        println("Fecha de nacimiento: $fechaNacimiento")
        println("Sexo: $sexo")
        println("Nacionalidad: $nacionalidad")
        println("Lugar de nacimiento: $lugarNacimiento")
        println("Domicilio: $domicilio")
        println("Clave de Elector: $claveElectoral")
        println("------------------------------")
        println("Fotografía: (imagen)")
        println("Firma: (imagen)")
        println("------------------------------")
    }
}

fun main() {
    val registro = Registro(
        "Juan Pérez",
        "01/01/1990",
        "Masculino",
        "Mexicana",
        "Ciudad de México",
        "Calle 123, Colonia Centro, CDMX",
        "ABCDE123456FGH789",
        null, // Aquí puedes colocar la imagen correspondiente
        null // Aquí puedes colocar la imagen correspondiente
    )

    registro.mostrarRegistro()
}
