package com.example.dailymileageapp.ui.theme // Make sure this package name matches your project structure

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp), // You can adjust this, 4.dp was common in Material 2
    large = RoundedCornerShape(16.dp)  // You can adjust this, 0.dp was common in Material 2 for some large components
)