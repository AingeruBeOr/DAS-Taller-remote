package com.example.proyecto1.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.repositories.ServicioRepository
import java.time.LocalDate
import java.time.Month
import javax.inject.Inject

class DownloadMonthServices @Inject constructor(
    val servicioRepository: ServicioRepository
){
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun downloadMonthServices() {
        val currentMonth = getCurrentMonth()
        val currentYear = getCurrentYear()
        val allServices = servicioRepository.getAllServiciosAsList()
        val monthServices = getMonthServices(currentMonth, currentYear, allServices)
        
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentMonth(): Month {
        val currentDate = LocalDate.now()
        return currentDate.month
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentYear(): Int {
        val currentDate = LocalDate.now()
        return currentDate.year
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMonthServices(
        currentMonth: Month,
        currentYear: Int,
        allServices: List<Servicio>
    ) : List<Servicio> {
        val monthServices = mutableListOf<Servicio>()
        allServices.forEach {
            val split = it.fecha.split("/")
            val month = split[1].toInt()
            val year = split[2].toInt()
            if (year == currentYear && month == currentMonth.value) monthServices.add(it)
        }
        return monthServices
    }
}
