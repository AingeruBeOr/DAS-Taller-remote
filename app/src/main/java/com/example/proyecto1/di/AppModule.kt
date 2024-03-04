package com.example.proyecto1.di

import android.content.Context
import androidx.room.Room
import com.example.proyecto1.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Este tipo de objeto se suele usar en HILT cuando no podemos usar la etiqueta @Inject. Esto puede
 * suceder por varias razones:
 * - Intentar injectar interfaces
 * - Intentar injectar clases de librerías externas
 * - ...
 */

@Module
@InstallIn(SingletonComponent::class) // Indicamos el alcance. SingletonComponent indica a nivel de aplicación
object AppModule {

    /**
     * Cremos la función proveedora de la base de datos
     */
    @Singleton // Tiene que ser una instancia única
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context) {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "taller-db").build()
    }

    /**
     * Como las interfaces no se puede  y los DAOs son interfaces, vamos a crearlas aquí.
     * Todas van a ser Singleton ya que solo vamos a querer una sola instancia de cada DAO
     */

    @Singleton
    @Provides
    fun provideClienteDao(db: AppDatabase) = db.clienteDao()

    @Singleton
    @Provides
    fun provideVehiculoDao(db: AppDatabase) = db.vehiculoDao()

    @Singleton
    @Provides
    fun provideServicioDao(db: AppDatabase) = db.servicioDao()
}