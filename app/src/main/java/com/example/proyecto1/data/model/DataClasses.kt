package com.example.proyecto1.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


// DEFINING data classes AND ROOM DATABASE TABLES

@Entity(tableName = "servicios")
data class Servicio(
    @PrimaryKey val fecha: String,
    val descripcion: String,
    val matricula: String,
)

@Entity(tableName = "vehiculos")
data class Vehiculo(
    @PrimaryKey val matricula: String,
    val marca: String,
    val modelo: String
)

@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey val nombre: String,
    val telefono: Int,
    val email: String
)