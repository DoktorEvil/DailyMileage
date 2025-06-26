package com.example.dailymileageapp // Ensure this package matches

import kotlinx.serialization.Serializable

@Serializable // If you are using Kotlinx Serialization
data class DailyMileage(
    val dayName: String,
    val mileage: String
)