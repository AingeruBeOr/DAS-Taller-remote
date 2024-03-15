package com.example.proyecto1.domain

import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.proyecto1.data.database.entities.Servicio
import com.example.proyecto1.data.repositories.ServicioRepository
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.OutputStreamWriter
import java.time.LocalDate
import java.time.Month
import javax.inject.Inject


class DownloadMonthServices @Inject constructor(
    val servicioRepository: ServicioRepository
){

    private fun List<Servicio>.toText() : String {
        var returnString = ""
        for (servicio in this) {
            returnString += "%%% ${servicio.fecha} -- ${servicio.matricula} %%%\n" +
                            "${servicio.descripcion}\n\n"
        }
        return returnString
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun downloadMonthServices() {
        val currentMonth = getCurrentMonth()
        val currentYear = getCurrentYear()
        val allServices = servicioRepository.getAllServiciosAsList()
        val monthServices = getMonthServices(currentMonth, currentYear, allServices)
        downloadToTxt(monthServices.toText(), currentMonth.value, currentYear)
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

    private fun downloadToTxt(
        text: String,
        currentMonth: Int,
        currentYear: Int
    ) {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "$currentMonth-$currentYear.txt"

        val file = File(dir.path, fileName)
        if (file.exists()) file.delete()
        try {
            val buf = BufferedWriter(FileWriter(file, true))
            buf.append(text)
            buf.newLine()
            buf.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.d("Download", "File downloaded successfully in: ${dir.path}/$currentMonth-$currentYear.txt")
    }
}
