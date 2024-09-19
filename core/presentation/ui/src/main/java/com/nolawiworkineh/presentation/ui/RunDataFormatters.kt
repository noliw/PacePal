package com.nolawiworkineh.presentation.ui

import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.time.Duration


// **Formats Duration to a string in the format "HH:MM:SS"**
fun Duration.formatted(): String {
    val totalSeconds = inWholeSeconds  // **Extracts the total number of seconds** from the Duration.
    val hours = String.format("%02d", totalSeconds / (60 * 60))  // **Calculates the hours**.
    val minutes = String.format("%02d", (totalSeconds % 3600) / 60)  // **Calculates the minutes**.
    val seconds = String.format("%02d", (totalSeconds % 60))  // **Calculates the remaining seconds**.

    return "$hours:$minutes:$seconds"  // **Returns** the formatted string "HH:MM:SS".
}

// **Formats a distance (in kilometers) to a string with one decimal place and "km" suffix**
fun Double.toFormattedKm(): String {
    return "${this.roundToDecimals(1)} km"  // **Rounds the distance to 1 decimal place** and appends " km".
}

// **Rounds a double to a specified number of decimal places**
private fun Double.roundToDecimals(decimalCount: Int): Double {
    val factor = 10f.pow(decimalCount)  // **Creates a factor** to multiply the number by (for rounding).
    return round(this * factor) / factor  // **Rounds the number** and divides by the factor to get the final value.
}

// **Formats the average pace (time per kilometer) in the format "MM:SS / km"**
fun Duration.toFormattedPace(distanceKm: Double): String {
    if (this == Duration.ZERO || distanceKm <= 0.0) {  // **Check if either time or distance is invalid**.
        return "-"  // **Returns "-" if the data is invalid**.
    }

    // **Calculates the average pace in seconds per kilometer**.
    val secondsPerKm = (this.inWholeSeconds / distanceKm).roundToInt()

    val avgPaceMinutes = secondsPerKm / 60  // **Extracts the minutes** for the pace.
    val avgPaceSeconds = String.format("%02d", secondsPerKm % 60)  // **Extracts the remaining seconds** for the pace.

    return "$avgPaceMinutes:$avgPaceSeconds / km"  // **Returns** the formatted string "MM:SS / km".
}
