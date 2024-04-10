package com.example.proyecto1.di

import android.content.Context
import androidx.room.Room
import com.example.proyecto1.data.database.AppDatabase
import com.example.proyecto1.data.repositories.PreferencesRepository
import com.example.proyecto1.network.RemoteDBApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton
/**
 * Este tipo de objeto se suele usar en HILT cuando no podemos usar la etiqueta @Inject. Esto puede
 * suceder por varias razones:
 * - Intentar injectar interfaces
 * - Intentar injectar clases de librerías externas
 * - ...
 */

@Module
@InstallIn(SingletonComponent::class) // Indicamos el alcance. 'SingletonComponent' indica a nivel de aplicación
object AppModule {


    // --- ROOM ----

    /**
     * Cremos la función proveedora de la base de datos
     */
    @Singleton // Tiene que ser una instancia única
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context) =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "taller-db")
            .fallbackToDestructiveMigration() // re-create the database instead of crashing.
            .createFromAsset("database/taller-db.db") // used to prepopulate (default data in database)
            .build()

    /*
     * Como las interfaces no se puede inyectar y los DAOs son interfaces, vamos a crearlas aquí.
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




    // ---- DataStore ---

    // We need to do this in order to inject ApplicationContext to PreferencesRepository
    @Singleton
    @Provides
    fun providePreferencesRepository(@ApplicationContext applicationContext: Context) =
        PreferencesRepository(applicationContext)



    // ---- Retrofit ----
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val baseUrl = "http://34.155.61.4/"
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(RemoteDBApiService::class.java)
}

