package com.example.dailymileageapp

import android.R.attr.id

// Make sure this matches your package name


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailymileageapp.ui.theme.DailyMileageAppTheme // You might need to adjust this import based on your theme name

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyMileageAppTheme { // This applies your app's theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MileageTrackerScreen()
                }
            }
        }
    }
}

// Data class to hold mileage information for a day
data class DailyMileage(val dayName: String, var mileage: String = "")

@Composable
fun MileageTrackerScreen() {
    // List of days
    val daysOfWeek = listOf(
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    )

    // State to hold the mileage for each day.
    // We use remember to keep the state across recompositions.
    // `mutableStateListOf` is good for lists where items themselves might change.
    val dailyMileages = remember {
        mutableStateListOf<DailyMileage>().apply {
            daysOfWeek.forEach { add(DailyMileage(it)) }
        }
    }

    // Calculate total mileage
    val totalMileage = dailyMileages.sumOf { it.mileage.toDoubleOrNull() ?: 0.0 }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Daily Mileage Tracker",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Create a row for each day with a label and an input field
        dailyMileages.forEachIndexed { index, dailyEntry ->
            MileageInputRow(
                dayName = dailyEntry.dayName,
                mileage = dailyEntry.mileage,
                onMileageChange = { newMileage ->
                    // Update the mileage in our state list
                    // Important: To trigger recomposition for mutableStateListOf when an item's property changes,
                    // you need to replace the item or ensure the list itself is observed for such changes.
                    // For simplicity here, we're creating a new list if an item is modified.
                    // A more optimized way for complex objects in a list might involve making DailyMileage.mileage a MutableState.
                    dailyMileages[index] = dailyMileages[index].copy(mileage = newMileage)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Total Mileage: %.2f km".format(totalMileage),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun MileageInputRow(
    dayName: String,
    mileage: String,
    onMileageChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = dayName,
            modifier = Modifier.weight(1f) // Day name takes up available space
        )
        OutlinedTextField(
            value = mileage,
            onValueChange = { newValue ->
                // Allow only numbers and a single decimal point
                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*\$"))) {
                    onMileageChange(newValue)
                }
            },
            label = { Text("km") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .weight(1f) // Input field also takes up space
                .padding(start = 8.dp)
        )
    }
}

// Preview function to see your Composable in Android Studio's design view
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DailyMileageAppTheme {
        MileageTrackerScreen()
    }
}