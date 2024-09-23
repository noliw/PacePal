package com.nolawiworkineh.run.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.nolawiworkineh.core.domain.location.LocationWithAltitude
import com.nolawiworkineh.run.domain.LocationObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidLocationObserver(
    private val context: Context
) : LocationObserver { // Implementing LocationObserver interface for observing location changes

    // Client to access the Fused Location Provider for retrieving the user's location (GPS, Network)
    private val client = LocationServices.getFusedLocationProviderClient(context)

    // Overriding the method to observe location changes at a given interval
    override fun observeLocation(interval: Long): Flow<LocationWithAltitude> {
        return callbackFlow { // Creating a flow that emits location data
            // Accessing the system's LocationManager service to manage location providers
            val locationManager = context.getSystemService<LocationManager>()!!

            // Variables to track whether GPS and Network Providers are enabled
            var isGpsEnabled = false
            var isNetworkEnabled = false

            // Continuously checking if GPS or Network Provider is enabled
            while (!isGpsEnabled && !isNetworkEnabled) {
                isGpsEnabled =
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) // Checking GPS status
                isNetworkEnabled =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) // Checking Network status

                // If neither GPS nor Network Provider is enabled, wait for 3 seconds before checking again
                if (!isGpsEnabled && !isNetworkEnabled) {
                    delay(3000L) // Delaying the loop to avoid constantly querying the providers
                }

            }


            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                close()
            } else {
                // Accessing the last known location using the locationProvider (previously named client)
                client.lastLocation.addOnSuccessListener {
                    // 'it' refers to the last known location, which may be null
                    it?.let { location ->
                        // If the location is not null, we send it through the flow by converting it to LocationWithAltitude
                        trySend(location.toLocationWithAltitude())
                    }
                }
                // Creating a LocationRequest with high accuracy and the specified interval
                val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, interval)
                    .build() // Builds the location request with the specified settings

                // Defining a callback object that listens for location updates
                val locationCallback = object : LocationCallback() {
                    // This method is triggered when the location result is received
                    override fun onLocationResult(result: LocationResult) {
                        super.onLocationResult(result)
                        // Get the last location from the result and send it through the flow
                        result.locations.lastOrNull()?.let { location ->
                            trySend(location.toLocationWithAltitude())
                        }
                    }
                }

                // Requesting location updates from the location provider with the specified request and callback
                client.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())

                // Ensures that when the flow is closed, location updates are stopped
                awaitClose {
                    // Removes the location updates when the observer is done
                    client.removeLocationUpdates(locationCallback)
                }
            }

        }
    }
}

