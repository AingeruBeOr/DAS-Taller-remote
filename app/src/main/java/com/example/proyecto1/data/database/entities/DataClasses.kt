package com.example.proyecto1.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


/* Se definien como data class las entidades de la base de datos, es decir, las tablas. */

@Serializable // to convert from JSON to this data class
@Entity(tableName = "servicios", primaryKeys = ["fecha", "descripcion"])
data class Servicio(
    val fecha: String,
    val descripcion: String,
    val matricula: String,
)

@Serializable // to convert from JSON to this data class
@Entity(tableName = "vehiculos")
data class Vehiculo(
    @PrimaryKey val matricula: String,
    val marca: String,
    val modelo: String,
    val nombreCliente: String
)

@Serializable // to convert from JSON to this data class
@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey val nombre: String,
    val telefono: Int,
    val email: String
)