package com.example.proyecto1.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo
import com.example.proyecto1.data.database.dao.ClienteDao
import com.example.proyecto1.data.database.dao.ServicioDao
import com.example.proyecto1.data.database.dao.VehiculoDao

@Database(entities = [Cliente::class, Vehiculo::class, Servicio::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    abstract fun vehiculoDao(): VehiculoDao
    abstract fun servicioDao(): ServicioDao
}