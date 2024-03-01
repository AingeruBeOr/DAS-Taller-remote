package com.example.proyecto1

data class Servicio(
    val fecha: String,
    val descripcion: String,
    val matricula: String,
)

data class Vehiculo(
    val matricula: String,
    val marca: String,
    val modelo: String
)

data class Cliente(
    val nombre: String,
    val telefono: Int,
    val email: String
)