package com.example.proyecto1.network

import kotlinx.serialization.Serializable

@Serializable // to convert from JSON to this data class
data class Message(
    val message: String
)
