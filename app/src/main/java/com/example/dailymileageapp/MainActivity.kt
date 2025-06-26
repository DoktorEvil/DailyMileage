package com.example.dailymileageapp // Ensure this package matches your project structure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailymileageapp.ui.theme.DailyMileageAppTheme
import kotlinx.coroutines.launch
import kotlin.collections.addAll
import kotlin.collections.forEachIndexed
import kotlin.collections.isNotEmpty
import kotlin.collections.sumOf
import kotlin.collections.toMutableList

// import kotlinx.serialization.Serializable // Removed if DailyMileage was the only class using it here

// DailyMileage data class has been moved to its own file: DailyMileage.kt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyMileageAppTheme {
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

@OptIn(ExperimentalMaterial3Api::class) // For Material 3 components like TopAppBar
@Composable
fun MileageTrackerScreen() {
    val context = LocalContext.current
    val dataStore = remember { MileageDataStore(context) } // MileageDataStore is in its own file
    val coroutineScope = rememberCoroutineScope()

    // Explicitly type dailyMileagesState
    // DailyMileage is now resolved from its own file in the same package
    val dailyMileagesState: State<List<DailyMileage>> =
        dataStore.dailyMileageFlow.collectAsState(initial = dataStore.getDefaultMileageList())

    val dailyMileages = remember { mutableStateListOf<DailyMileage>() }

    LaunchedEffect(dailyMileagesState.value) {
        dailyMileages.clear()
        dailyMileages.addAll(dailyMileagesState.value)
    }

    val totalMileage = dailyMileages.sumOf { it.mileage.toDoubleOrNull() ?: 0.0 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Mileage Tracker") },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            dataStore.clearMileageData()
                        }
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Clear Data")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (dailyMileages.isNotEmpty() || dailyMileagesState.value.isEmpty()) {
                dailyMileages.forEachIndexed { index, dailyEntry ->
                    if (index < dailyMileages.size) { // Defensive check
                        MileageInputRow(
                            dayName = dailyEntry.dayName,
                            mileage = dailyEntry.mileage,
                            onMileageChange = { newMileage ->
                                val updatedList = dailyMileages.toMutableList().apply {
                                    this[index] = this[index].copy(mileage = newMileage)
                                }
                                dailyMileages.clear()
                                dailyMileages.addAll(updatedList)

                                coroutineScope.launch {
                                    dataStore.saveMileageList(updatedList)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else if (dailyMileagesState.value.isNotEmpty()) {
                // Optional: Show a loading indicator or placeholder
                // CircularProgressIndicator()
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Total Mileage: %.2f km".format(totalMileage),
                style = MaterialTheme.typography.titleLarge
            )
        }
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
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = mileage,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*\$"))) {
                    onMileageChange(newValue)
                }
            },
            label = { Text("km") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DailyMileageAppTheme {
        MileageTrackerScreen()
    }
}