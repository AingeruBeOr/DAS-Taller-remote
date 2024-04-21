package com.example.proyecto1.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyecto1.data.database.dao.ClienteDao
import com.example.proyecto1.data.database.dao.ServicioDao
import com.example.proyecto1.data.database.dao.VehiculoDao
import com.example.proyecto1.data.database.entities.Cliente
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.database.entities.Vehiculo

/**
 * Base de datos Room de la aplicación. En esta hay que definir cuales son las entidades y cuales
 * son los DAO (Data Access Object) que acceden a estos datos.
 */
@Database(
    entities = [Cliente::class, Vehiculo::class, Servicio::class],
    version = 2,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    abstract fun vehiculoDao(): VehiculoDao
    abstract fun servicioDao(): ServicioDao
}