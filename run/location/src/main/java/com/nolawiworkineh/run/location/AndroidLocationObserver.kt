package com.nolawiworkineh.run.location

import android.content.Context
import android.location.LocationManager
import androidx.core.content.getSystemService
import com.google.android.gms.location.LocationServices
import com.nolawiworkineh.core.domain.location.LocationWithAltitude
import com.nolawiworkineh.run.domain.LocationObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidLocationObserver(
    private val context: Context
) : LocationObserver { // Implementing LocationObserver interface for observing location changes

    // Client to access the Fused Location Provider for retrieving the user's location
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
                isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) // Checking GPS status
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) // Checking Network status

                // If neither GPS nor Network Provider is enabled, wait for 3 seconds before checking again
                if (!isGpsEnabled && !isNetworkEnabled) {
                    delay(3000L) // Delaying the loop to avoid constantly querying the providers
                }
            }

            // Other logic to get location updates will be implemented here...
        }
    }
}
